package com.prt2121.currentaddress

import org.robovm.apple.corelocation.CLPlacemark
import org.robovm.apple.foundation.NSIndexPath
import org.robovm.apple.foundation.NSIndexSet
import org.robovm.apple.uikit.UITableViewController
import org.robovm.apple.uikit.UITableViewRowAnimation
import org.robovm.objc.annotation.CustomClass

/**
 * Created by pt2121 on 11/28/15.
 */
@CustomClass("PlacemarkViewController")
class PlacemarkViewController : UITableViewController() {
  var placemark: CLPlacemark? = null

  override fun viewWillAppear(animated: Boolean) {
    super.viewWillAppear(animated)

    // Get the thoroughfare table cell and set the detail text to show the
    // thoroughfare.
    var cell = tableView.getCellForRow(NSIndexPath.row(0, 0))
    cell.detailTextLabel.text = placemark!!.thoroughfare

    // Get the sub-thoroughfare table cell and set the detail text to show
    // the sub-thoroughfare.
    cell = tableView.getCellForRow(NSIndexPath.row(1, 0))
    cell.detailTextLabel.text = placemark!!.subThoroughfare

    // Get the locality table cell and set the detail text to show the
    // locality.
    cell = tableView.getCellForRow(NSIndexPath.row(2, 0))
    cell.detailTextLabel.text = placemark!!.locality

    // Get the sub-locality table cell and set the detail text to show the
    // sub-locality.
    cell = tableView.getCellForRow(NSIndexPath.row(3, 0))
    cell.detailTextLabel.text = placemark!!.subLocality

    // Get the administrative area table cell and set the detail text to
    // show the administrative area.
    cell = tableView.getCellForRow(NSIndexPath.row(4, 0))
    cell.detailTextLabel.text = placemark!!.administrativeArea

    // Get the sub-administrative area table cell and set the detail text to
    // show the sub-administrative area.
    cell = tableView.getCellForRow(NSIndexPath.row(5, 0))
    cell.detailTextLabel.text = placemark!!.subAdministrativeArea

    // Get the postal code table cell and set the detail text to show the
    // postal code.
    cell = tableView.getCellForRow(NSIndexPath.row(6, 0))
    cell.detailTextLabel.text = placemark!!.postalCode

    // Get the country table cell and set the detail text to show the
    // country.
    cell = tableView.getCellForRow(NSIndexPath.row(7, 0))
    cell.detailTextLabel.text = placemark!!.country

    // Get the ISO country code table cell and set the detail text to show
    // the ISO country code.
    cell = tableView.getCellForRow(NSIndexPath.row(8, 0))
    cell.detailTextLabel.text = placemark!!.isOcountryCode

    // Tell the table to reload section zero of the table.
    tableView.reloadSections(NSIndexSet(0), UITableViewRowAnimation.None)
  }
}