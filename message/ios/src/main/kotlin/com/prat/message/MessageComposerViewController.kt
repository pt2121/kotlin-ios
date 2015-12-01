package com.prat.message

import org.robovm.apple.foundation.NSBundle
import org.robovm.apple.foundation.NSData
import org.robovm.apple.foundation.NSError
import org.robovm.apple.messageui.*
import org.robovm.apple.uikit.UILabel
import org.robovm.apple.uikit.UIViewController
import org.robovm.objc.annotation.CustomClass
import org.robovm.objc.annotation.IBAction
import org.robovm.objc.annotation.IBOutlet
import java.io.File
import java.util.*

@CustomClass("MessageComposerViewController")
class MessageComposerViewController : UIViewController(), MFMailComposeViewControllerDelegate, MFMessageComposeViewControllerDelegate {
  /* UILabel for displaying the result of sending the message. */
  @IBOutlet
  private val feedbackMsg: UILabel? = null

  @IBAction
  private fun showMailPicker() {
    // http://stackoverflow.com/questions/25895634/email-composure-ios-8
    // MIGHT NOT WORK ON EMULATOR
    /*
     * You must check that the current device can send email messages before
     * you attempt to create an instance of MFMailComposeViewController.
     * Otherwise your app will crash when it creates a new
     * MFMailComposeViewController.
     */
    if (MFMailComposeViewController.canSendMail()) {
      // The device can send email.
      displayMailComposerSheet()
    } else {
      // The device can not send email.
      feedbackMsg!!.isHidden = false
      feedbackMsg.text = "Device not configured to send mail."
    }
  }

  @IBAction
  private fun showSMSPicker() {
    /*
     * You must check that the current device can send SMS messages before
     * you attempt to create an instance of MFMessageComposeViewController.
     * Otherwise your app will crash when it creates a new
     * MFMessageComposeViewController.
     */
    if (MFMessageComposeViewController.canSendText()) {
      // The device can send SMS.
      displaySMSComposerSheet()
    } else {
      // The device can not send email.
      feedbackMsg!!.isHidden = false
      feedbackMsg.text = "Device not configured to send SMS."
    }
  }

  /**
   * Displays an email composition interface inside the application. Populates
   * all the Mail fields.
   */
  private fun displayMailComposerSheet() {
    val picker = MFMailComposeViewController()
    picker.mailComposeDelegate = this
    picker.setSubject("Hello from California!")

    // Set up recipients
    picker.setToRecipients(Arrays.asList("first@example.com"))
    picker.setCcRecipients(Arrays.asList("second@example.com", "third@example.com"))
    picker.setBccRecipients(Arrays.asList("fourth@example.com"))

    // Attach an image to the email
    val path = NSBundle.getMainBundle().findResourcePath("rainy", "jpg")
    val myData = NSData.read(File(path))
    picker.addAttachmentData(myData, "image/jpeg", "rainy")

    // Fill out the email body text
    val emailBody = "It is raining in sunny California!"
    picker.setMessageBody(emailBody, false)

    presentViewController(picker, true, null)
  }

  /** Displays an SMS composition interface inside the application.  */
  private fun displaySMSComposerSheet() {
    val picker = MFMessageComposeViewController()
    picker.messageComposeDelegate = this
    /*
     * You can specify one or more preconfigured recipients. The user has
     * the option to remove or add recipients from the message composer view
     * controller.
     */
    /* picker.setRecipients(NSArray.toNSArray("Phone number here")); */

    /*
     * You can specify the initial message text that will appear in the
     * message composer view controller.
     */
    picker.body = "Hello from California!"

    presentViewController(picker, true, null)
  }

  /**
   * Dismisses the email composition interface when users tap Cancel or Send.
   * Proceeds to update the message field with the result of the operation.
   */
  override fun didFinish(controller: MFMailComposeViewController, result: MFMailComposeResult, error: NSError?) {
    feedbackMsg!!.isHidden = false

    // Notifies users about errors associated with the interface
    feedbackMsg.text = when (result) {
      MFMailComposeResult.Cancelled -> "Result: Mail sending canceled"
      MFMailComposeResult.Saved -> "Result: Mail saved"
      MFMailComposeResult.Sent -> "Result: Mail sent"
      MFMailComposeResult.Failed -> "Result: Mail sending failed"
      else -> "Result: Mail not sent"
    }

    dismissViewController(true, null)
  }

  /**
   * Dismisses the message composition interface when users tap Cancel or
   * Send. Proceeds to update the feedback message field with the result of
   * the operation.
   */
  override fun didFinish(controller: MFMessageComposeViewController, result: MFMessageComposeResult) {
    feedbackMsg!!.isHidden = false

    // Notifies users about errors associated with the interface
    feedbackMsg.text = when (result) {
      MFMessageComposeResult.Cancelled -> "Result: SMS sending canceled"
      MFMessageComposeResult.Sent -> "Result: SMS sent"
      MFMessageComposeResult.Failed -> "Result: SMS sending failed"
      else -> "Result: SMS not sent"
    }

    dismissViewController(true, null)
  }
}

