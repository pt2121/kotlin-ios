package com.prat.tablesearch

/**
 * Created by pt2121 on 11/27/15.
 */
import org.robovm.apple.contacts.CNContact
import org.robovm.apple.foundation.NSCoder
import org.robovm.apple.uikit.UILabel
import org.robovm.apple.uikit.UIViewController
import org.robovm.objc.annotation.CustomClass
import org.robovm.objc.annotation.IBOutlet

@CustomClass("DetailViewController")
class DetailViewController : UIViewController() {

  private var contact: CNContact? = null
  @IBOutlet
  private val emailLabel: UILabel? = null
  @IBOutlet
  private val phoneNumberLabel: UILabel? = null

  override fun viewWillAppear(animated: Boolean) {
    super.viewWillAppear(animated)
    title = "${contact!!.givenName} ${contact!!.familyName}"
    emailLabel!!.text = if (contact!!.emailAddresses.isNotEmpty()) contact!!.emailAddresses[0].value else ""
    phoneNumberLabel!!.text = contact!!.phoneNumbers[0].value.stringValue
  }

  fun setContact(contact: CNContact) {
    this.contact = contact
  }

  override fun encodeRestorableState(coder: NSCoder) {
    super.encodeRestorableState(coder)

    // encode the product
    coder.encodeObject(VIEW_CONTROLLER_CONTACT_KEY, contact)
  }

  override fun decodeRestorableState(coder: NSCoder) {
    super.decodeRestorableState(coder)

    // restore the contact
    contact = coder.decodeObject(VIEW_CONTROLLER_CONTACT_KEY, CNContact::class.java)
  }

  companion object {
    private val VIEW_CONTROLLER_CONTACT_KEY = "ViewControllerContactKey"
  }
}