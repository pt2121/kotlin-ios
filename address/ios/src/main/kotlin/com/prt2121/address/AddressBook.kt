package com.prt2121.address

import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication
import org.robovm.apple.uikit.UIApplicationDelegateAdapter
import org.robovm.apple.uikit.UIApplicationLaunchOptions

class AddressBook : UIApplicationDelegateAdapter() {

  override fun didFinishLaunching(application: UIApplication?, launchOptions: UIApplicationLaunchOptions?): Boolean {
    return true
  }

  companion object {
    @JvmStatic fun main(args: Array<String>) {
      val pool = NSAutoreleasePool()
      UIApplication.main<UIApplication, AddressBook>(args, null, AddressBook::class.java)
      pool.release()
    }
  }
}

