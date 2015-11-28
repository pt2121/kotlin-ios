package com.prt2121.currentaddress

import org.robovm.apple.corelocation.CLGeocoder
import org.robovm.apple.corelocation.CLLocationManager
import org.robovm.apple.corelocation.CLPlacemark
import org.robovm.apple.dispatch.Dispatch
import org.robovm.apple.foundation.Foundation
import org.robovm.apple.foundation.NSArray
import org.robovm.apple.foundation.NSError
import org.robovm.apple.foundation.NSObject
import org.robovm.apple.mapkit.MKMapView
import org.robovm.apple.mapkit.MKMapViewDelegateAdapter
import org.robovm.apple.mapkit.MKUserLocation
import org.robovm.apple.uikit.UIBarButtonItem
import org.robovm.apple.uikit.UIStoryboardSegue
import org.robovm.apple.uikit.UIViewController
import org.robovm.objc.annotation.CustomClass
import org.robovm.objc.annotation.IBOutlet
import org.robovm.objc.block.VoidBlock2

/**
 * Created by pt2121 on 11/28/15.
 */
@CustomClass("MapViewController")
class MapViewController : UIViewController() {
  @IBOutlet
  private val mapView: MKMapView? = null
  @IBOutlet
  private val addressButton: UIBarButtonItem? = null

  private var geocoder: CLGeocoder? = null
  private var placemark: CLPlacemark? = null

  override fun viewDidLoad() {
    // Create a geocoder and save it for later.
    geocoder = CLGeocoder()

    println("major system version ${Foundation.getMajorSystemVersion()}")

    if (Foundation.getMajorSystemVersion() >= 8) {
      val locationManager = CLLocationManager()
      locationManager.requestWhenInUseAuthorization()
    }

    mapView!!.delegate = object : MKMapViewDelegateAdapter() {
      override fun didUpdateUserLocation(mapView: MKMapView?, userLocation: MKUserLocation?) {
        // Center the map the first time we get a real location change.
        if ((userLocation!!.coordinate.latitude != 0.0) && (userLocation.coordinate.longitude != 0.0)) {
          Dispatch.once(object : Runnable {
            override fun run() {
              mapView!!.setCenterCoordinate(userLocation.coordinate, true)
            }
          })
        }

        // Lookup the information for the current location of the user.
        geocoder!!.reverseGeocodeLocation(mapView!!.userLocation.location,
            object : VoidBlock2<NSArray<CLPlacemark>, NSError> {
              override fun invoke(placemarks: NSArray<CLPlacemark>?, error: NSError?) {
                if (placemarks != null && placemarks.size > 0) {
                  // If the placemark is not null then we have
                  // at least one placemark. Typically there
                  // will only be
                  // one.
                  placemark = placemarks[0]
                  val coor = placemark!!.location.coordinate
                  println("lat ${coor.latitude}, long ${coor.longitude}")

                  // we have received our current location, so
                  // enable the "Get Current Address" button
                  addressButton!!.isEnabled = true
                } else {
                  // Handle the null case if necessary.
                }
              }
            })
      }
    }
  }

  override fun prepareForSegue(segue: UIStoryboardSegue, sender: NSObject) {
    if (segue.identifier == "pushToDetail") {
      // Get the destination view controller and set the placemark data
      // that it should display.
      val viewController = segue.destinationViewController as PlacemarkViewController
      viewController.placemark = placemark
    }
  }
}