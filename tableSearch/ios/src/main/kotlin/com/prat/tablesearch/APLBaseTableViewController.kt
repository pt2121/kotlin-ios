package com.prat.tablesearch

import org.robovm.apple.foundation.NSNumber
import org.robovm.apple.foundation.NSNumberFormatter
import org.robovm.apple.foundation.NSNumberFormatterStyle
import org.robovm.apple.uikit.UINib
import org.robovm.apple.uikit.UITableViewCell
import org.robovm.apple.uikit.UITableViewController
import org.robovm.objc.annotation.CustomClass

@CustomClass("APLBaseTableViewController")
open class APLBaseTableViewController : UITableViewController() {
  internal val CELL_IDENTIFIER = "cellID"
  private val TABLE_CELL_NIB_NAME = "TableCell"

  override fun viewDidLoad() {
    super.viewDidLoad()

    // we use a nib which contains the cell's view and this class as the
    // files owner
    tableView.registerReusableCellNib(UINib(TABLE_CELL_NIB_NAME, null), CELL_IDENTIFIER)
  }

  fun configureCell(cell: UITableViewCell, product: APLProduct) {
    cell.textLabel.text = product.title

    // build the price and year string
    // use NSNumberFormatter to get the currency format out of this NSNumber
    // (product.introPrice)
    val numFormatter = NSNumberFormatter()
    numFormatter.numberStyle = NSNumberFormatterStyle.Currency
    val priceString = numFormatter.format(NSNumber.valueOf(product.introPrice))

    val detailedStr = "$priceString | ${product.yearIntroduced}"
    cell.detailTextLabel.text = detailedStr
  }
}