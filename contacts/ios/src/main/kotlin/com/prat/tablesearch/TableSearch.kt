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
    return true
  }

  override fun shouldSaveApplicationState(application: UIApplication?, coder: NSCoder?): Boolean {
    return true
  }

  override fun shouldRestoreApplicationState(application: UIApplication?, coder: NSCoder?): Boolean {
    return true
  }

}

