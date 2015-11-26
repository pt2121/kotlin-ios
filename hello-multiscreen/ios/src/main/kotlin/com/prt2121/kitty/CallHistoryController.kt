package com.prt2121.kitty

import org.robovm.apple.foundation.NSIndexPath
import org.robovm.apple.uikit.UITableView
import org.robovm.apple.uikit.UITableViewCell
import org.robovm.apple.uikit.UITableViewCellStyle
import org.robovm.apple.uikit.UITableViewController
import org.robovm.objc.annotation.CustomClass

/**
 * Created by pt2121 on 11/26/15.
 */
@CustomClass("CallHistoryController")
class CallHistoryController : UITableViewController() {
  val callHistoryCellId = "CallHistoryCell"
  var phoneNumbers = emptyList<String>()

  override fun viewDidLoad() {
    super.viewDidLoad()
  }

  override fun getNumberOfSections(tableView: UITableView?): Long {
    return 1
  }

  override fun getNumberOfRowsInSection(tableView: UITableView?, section: Long): Long {
    return phoneNumbers.size.toLong()
  }

  override fun getCellForRow(tableView: UITableView?, indexPath: NSIndexPath?): UITableViewCell? {
    val row = indexPath!!.row
    var cell = tableView!!.dequeueReusableCell(callHistoryCellId)
    if (cell == null) {
      cell = UITableViewCell(UITableViewCellStyle.Default, callHistoryCellId)
    }
    cell.textLabel.text = phoneNumbers[row]
    return cell
  }
}