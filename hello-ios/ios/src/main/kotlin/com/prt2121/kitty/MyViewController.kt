package com.prt2121.kitty

import org.robovm.apple.foundation.NSURL
import org.robovm.apple.uikit.*
import org.robovm.objc.annotation.CustomClass
import org.robovm.objc.annotation.IBAction
import org.robovm.objc.annotation.IBOutlet

@CustomClass("MyViewController")
class MyViewController : UIViewController() {
  @IBOutlet
  private val textField: UITextField? = null

  @IBOutlet
  private val callButton: UIButton? = null

  var translatedNumber: String? = null

  @IBAction
  private fun clickedTranslate() {
    translatedNumber = PhoneTranslator.toNumber(textField?.text)
    textField!!.resignFirstResponder()
    if(translatedNumber.isNullOrBlank()) {
      println("isNullOrBlank")
      callButton!!.setTitle("Call ", UIControlState.Normal)
      callButton.isEnabled = false
    } else {
      println(translatedNumber)
      callButton!!.setTitle("Call " + translatedNumber, UIControlState.Normal)
      callButton.isEnabled = true
    }
  }

  @IBAction
  private fun clickedCall() {
    val url = NSURL("tel:" + translatedNumber)
    if(!UIApplication.getSharedApplication().openURL(url)) {
      val alert = UIAlertController("Not supported", "Scheme 'tel:' is not supported on this device",
          UIAlertControllerStyle.Alert)
      presentViewController(alert, true, null)
    }
  }
}

