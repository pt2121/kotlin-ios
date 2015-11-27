package com.prat.tablesearch

import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.foundation.NSCoder
import org.robovm.apple.uikit.UIApplication
import org.robovm.apple.uikit.UIApplicationDelegateAdapter
import org.robovm.apple.uikit.UIApplicationLaunchOptions
import org.robovm.apple.uikit.UINavigationController

class TableSearch : UIApplicationDelegateAdapter() {

  companion object {
    @JvmStatic fun main(args: Array<String>) {
      val pool = NSAutoreleasePool()
      UIApplication.main<UIApplication, TableSearch>(args, null, TableSearch::class.java)
      pool.release()
    }
  }

  override fun didFinishLaunching(application: UIApplication?, launchOptions: UIApplicationLaunchOptions?): Boolean {
    // load our data source and hand it over to APLMainTableViewController
    val products = listOf(
        APLProduct(APLProduct.deviceTypeTitle, "iPhone", 2007, 599.00),
        APLProduct(APLProduct.deviceTypeTitle, "iPod", 2001, 399.00),
        APLProduct(APLProduct.deviceTypeTitle, "iPod touch", 2007, 210.00),
        APLProduct(APLProduct.deviceTypeTitle, "iPad", 2010, 499.00),
        APLProduct(APLProduct.deviceTypeTitle, "iPad mini", 2012, 659.00),
        APLProduct(APLProduct.deviceTypeTitle, "iMac", 1997, 1299.00),
        APLProduct(APLProduct.deviceTypeTitle, "Mac Pro", 2006, 2499.00),
        APLProduct(APLProduct.portableTypeTitle, "MacBook Air", 2008, 1799.00),
        APLProduct(APLProduct.portableTypeTitle, "MacBook Pro", 2006, 1499.00)
    )

    val navigationController = window.rootViewController as UINavigationController
    // note we want the first view controller (not the
    // visibleViewController) in case
    // we are being store from UIStateRestoration
    val viewController = navigationController.viewControllers[0] as APLMainTableViewController
    viewController.setProducts(products)

    return true
  }

  override fun shouldSaveApplicationState(application: UIApplication?, coder: NSCoder?): Boolean {
    return true
  }

  override fun shouldRestoreApplicationState(application: UIApplication?, coder: NSCoder?): Boolean {
    return true
  }

}

