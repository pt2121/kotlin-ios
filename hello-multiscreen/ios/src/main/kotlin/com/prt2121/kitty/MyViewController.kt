package com.prt2121.kitty

import org.robovm.apple.foundation.NSObject
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

  var phoneNumbers = emptyList<String>()

  override fun viewDidLoad() {
    super.viewDidLoad()
  }

  override fun prepareForSegue(segue: UIStoryboardSegue?, sender: NSObject?) {
    super.prepareForSegue(segue, sender)
    if (segue?.destinationViewController is CallHistoryController) {
      val controller = (segue?.destinationViewController as CallHistoryController)
      controller.phoneNumbers = phoneNumbers
    }
  }

  @IBAction
  private fun clickedTranslate() {
    translatedNumber = PhoneTranslator.toNumber(textField?.text)
    textField!!.resignFirstResponder()
    if (translatedNumber.isNullOrBlank()) {
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
    phoneNumbers = phoneNumbers.plus(translatedNumber!!)
    if (!UIApplication.getSharedApplication().openURL(url)) {
      val alert = UIAlertController("Not supported", "Scheme 'tel:' is not supported on this device",
          UIAlertControllerStyle.Alert)
      alert.addAction(UIAlertAction("Ok", UIAlertActionStyle.Default, null))
      presentViewController(alert, true, null)
    }
  }
}

