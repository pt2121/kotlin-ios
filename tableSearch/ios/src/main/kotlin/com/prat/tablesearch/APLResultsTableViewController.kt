package com.prat.tablesearch

/**
 * Created by pt2121 on 11/27/15.
 */


import org.robovm.apple.foundation.NSIndexPath
import org.robovm.apple.uikit.UITableView
import org.robovm.apple.uikit.UITableViewCell
import org.robovm.objc.annotation.CustomClass

@CustomClass("APLResultsTableViewController")
class APLResultsTableViewController : APLBaseTableViewController() {
  var filteredProducts: List<APLProduct>? = null

  override fun getNumberOfRowsInSection(tableView: UITableView, section: Long): Long {
    return filteredProducts!!.size.toLong()
  }

  override fun getCellForRow(tableView: UITableView, indexPath: NSIndexPath): UITableViewCell {
    val product = filteredProducts!![indexPath.row.toInt()]

    val cell = getTableView().dequeueReusableCell(CELL_IDENTIFIER)
    configureCell(cell, product)
    return cell
  }
}