package com.prat.webview

import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.UIApplication
import org.robovm.apple.uikit.UIApplicationDelegateAdapter
import org.robovm.apple.uikit.UIApplicationLaunchOptions

class Web : UIApplicationDelegateAdapter() {

  override fun didFinishLaunching(application: UIApplication?, launchOptions: UIApplicationLaunchOptions?): Boolean {
    return true
  }

  companion object {
    @JvmStatic fun main(args: Array<String>) {
      val pool = NSAutoreleasePool()
      UIApplication.main<UIApplication, Web>(args, null, Web::class.java)
      pool.release()
    }
  }
}

