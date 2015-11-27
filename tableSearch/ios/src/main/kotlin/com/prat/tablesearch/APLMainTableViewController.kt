package com.prat.tablesearch

import org.robovm.apple.foundation.NSCoder
import org.robovm.apple.foundation.NSIndexPath
import org.robovm.apple.uikit.*
import org.robovm.objc.annotation.CustomClass

/**
 * Created by pt2121 on 11/27/15.
 */
@CustomClass("APLMainTableViewController")
class APLMainTableViewController : APLBaseTableViewController(), UISearchResultsUpdating {

  private var products = emptyList<APLProduct>()
  private var searchController: UISearchController? = null
  // our secondary search results table view
  private var resultsTableController: APLResultsTableViewController? = null

  private var searchControllerWasActive: Boolean = false
  private var searchControllerSearchFieldWasFirstResponder: Boolean = false

  public fun setProducts(products: List<APLProduct>) {
    this.products = products
  }

  override fun viewDidLoad() {
    super.viewDidLoad()

    resultsTableController = APLResultsTableViewController()
    searchController = UISearchController(resultsTableController)
    searchController!!.searchResultsUpdater = this
    searchController!!.searchBar!!.sizeToFit()
    tableView.tableHeaderView = searchController!!.searchBar

    // we want to be the delegate for our filtered table so
    // didSelectRowAtIndexPath is called for both tables
    resultsTableController!!.tableView!!.setDelegate(this)// so we can
    // monitor text
    // changes +
    // others
    searchController!!.delegate = object : UISearchControllerDelegateAdapter() {
      /**
       * Called after the search controller's search bar has agreed to
       * begin editing or when 'active' is set to true. If you choose not
       * to present the controller yourself or do not implement this
       * method, a default presentation is performed on your behalf.

       * Implement this method if the default presentation is not adequate
       * for your purposes.
       */
      override fun present(searchController: UISearchController?) {
      }
    }
    searchController!!.setDimsBackgroundDuringPresentation(false)
    // so we can monitor text changes + others
    searchController!!.searchBar!!.delegate = object : UISearchBarDelegateAdapter() {
      override fun searchButtonClicked(searchBar: UISearchBar?) {
        searchBar!!.resignFirstResponder()
      }
    }

    // Search is now just presenting a view controller. As such, normal view
    // controller
    // presentation semantics apply. Namely that presentation will walk up
    // the view controller
    // hierarchy until it finds the root view controller or one that defines
    // a presentation context.
    setDefinesPresentationContext(true) // know where you want
    // UISearchController to be
    // displayed
  }

  override fun viewDidAppear(animated: Boolean) {
    super.viewDidAppear(animated)

    // restore the searchController's active state
    if (searchControllerWasActive) {
      searchController!!.isActive = searchControllerWasActive
      searchControllerWasActive = false

      if (searchControllerSearchFieldWasFirstResponder) {
        searchController!!.searchBar.becomeFirstResponder()
        searchControllerSearchFieldWasFirstResponder = false
      }
    }
  }

  override fun getNumberOfRowsInSection(tableView: UITableView, section: Long): Long {
    return products.size.toLong()
  }

  override fun getCellForRow(tableView: UITableView, indexPath: NSIndexPath): UITableViewCell {
    val product = products[indexPath.row.toInt()]

    val cell = getTableView().dequeueReusableCell(CELL_IDENTIFIER)
    configureCell(cell, product)
    return cell
  }

  override fun didSelectRow(tableView: UITableView, indexPath: NSIndexPath) {
    val selectedProduct = if (tableView == getTableView()) {
      products[indexPath.row.toInt()]
    } else {
      resultsTableController!!.filteredProducts!![indexPath.row.toInt()];
    }

    val detailViewController = storyboard.instantiateViewController("APLDetailViewController") as APLDetailViewController
    // hand off the current product to the detail view controller
    detailViewController.setProduct(selectedProduct)

    navigationController.pushViewController(detailViewController, true)

    tableView.deselectRow(indexPath, false)
  }

  override fun updateSearchResults(p0: UISearchController?) {
    // update the filtered array based on the search text
    val searchText = searchController!!.searchBar.text

    // strip out all the leading and trailing spaces
    val strippedStr = searchText.trim { it <= ' ' }

    // break up the search terms (separated by spaces)
    val searchItems = strippedStr.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

    // Find products with the specified search terms.
    val searchResults = products.filter { product ->
      searchItems.fold(false) { acc, s ->
        product.title.contains(s, true) || matchYear(s, product.yearIntroduced) || matchPrice(s, product.introPrice)
      }
    }

    val tableController = searchController!!.searchResultsController as APLResultsTableViewController
    tableController.filteredProducts = searchResults
    tableController.tableView.reloadData()
  }

  // we restore several items for state restoration:
  // 1) Search controller's active state,
  // 2) search text,
  // 3) first responder

  private val VIEW_CONTROLLER_TITLE_KEY = "ViewControllerTitleKey"
  private val SEARCH_CONTROLLER_IS_ACTIVE_KEY = "SearchControllerIsActiveKey"
  private val SEARCH_BAR_TEXT_KEY = "SearchBarTextKey"
  private val SEARCH_BAR_IS_FIRST_RESPONDER_KEY = "SearchBarIsFirstResponderKey"

  override fun encodeRestorableState(coder: NSCoder) {
    super.encodeRestorableState(coder)

    // encode the view state so it can be restored later

    // encode the title
    coder.encodeString(VIEW_CONTROLLER_TITLE_KEY, title)

    // encode the search controller's active state
    val searchDisplayControllerIsActive = searchController!!.isActive
    coder.encodeBoolean(SEARCH_CONTROLLER_IS_ACTIVE_KEY, searchDisplayControllerIsActive)

    // encode the first responder status
    if (searchDisplayControllerIsActive) {
      coder.encodeBoolean(SEARCH_BAR_IS_FIRST_RESPONDER_KEY, searchController!!.searchBar.isFirstResponder)
    }

    // encode the search bar text
    coder.encodeString(SEARCH_BAR_TEXT_KEY, searchController!!.searchBar.text)
  }

  override fun decodeRestorableState(coder: NSCoder) {
    super.decodeRestorableState(coder)

    // restore the title
    title = coder.decodeString(VIEW_CONTROLLER_TITLE_KEY)

    // restore the active state:
    // we can't make the searchController active here since it's not part of
    // the view
    // hierarchy yet, instead we do it in viewWillAppear
    searchControllerWasActive = coder.decodeBoolean(SEARCH_CONTROLLER_IS_ACTIVE_KEY)

    // restore the first responder status:
    // we can't make the searchController first responder here since it's
    // not part of the view
    // hierarchy yet, instead we do it in viewWillAppear
    searchControllerSearchFieldWasFirstResponder = coder.decodeBoolean(SEARCH_BAR_IS_FIRST_RESPONDER_KEY)

    // restore the text in the search field
    searchController!!.searchBar.text = coder.decodeString(SEARCH_BAR_TEXT_KEY)
  }

  private fun matchYear(s: String, year: Int): Boolean {
    try {
      val y = s.toInt()
      return y == year
    } catch(e: Exception) {
      return false
    }
  }

  private fun matchPrice(s: String, price: Double): Boolean {
    try {
      val p = s.toDouble()
      return Math.abs(price - p) <= 0.0000001
    } catch(e: Exception) {
      return false
    }
  }
}