package com.prt2121.address

import org.robovm.apple.addressbook.*
import org.robovm.apple.addressbookui.ABPeoplePickerNavigationController
import org.robovm.apple.addressbookui.ABPeoplePickerNavigationControllerDelegateAdapter
import org.robovm.apple.dispatch.DispatchQueue
import org.robovm.apple.foundation.NSError
import org.robovm.apple.foundation.NSErrorException
import org.robovm.apple.uikit.UIAlertView
import org.robovm.apple.uikit.UILabel
import org.robovm.apple.uikit.UIViewController
import org.robovm.objc.annotation.CustomClass
import org.robovm.objc.annotation.IBAction
import org.robovm.objc.annotation.IBOutlet
import java.util.*

@CustomClass("MyViewController")
class MyViewController : UIViewController() {
  private val counterStore = CounterStore()
  private var addressBook: ABAddressBook? = null

  @IBOutlet
  private val label: UILabel? = null

  @IBAction
  private fun clicked() {
    counterStore.add(1)
    label!!.text = "Click Nr. " + counterStore.get()
    showPeoplePickerController()
  }

  override fun viewDidLoad() {
    super.viewDidLoad()
    try {
      addressBook = ABAddressBook.create(null)
      checkAddressBookAccess()
    } catch (e: NSErrorException) {
      alert("Error", "Can't access to contacts.", "Cancel")
    }
  }

  private fun alert(title: String, message: String, buttonText: String) {
    val alert = UIAlertView(title, message, null, buttonText)
    alert.show()
  }

  /**
   * Check the authorization status of our application for Address Book
   */
  private fun checkAddressBookAccess() {
    when (ABAddressBook.getAuthorizationStatus()) {
      ABAuthorizationStatus.Authorized ->
        println("Authorized")
    //showPeoplePickerController()
      ABAuthorizationStatus.NotDetermined ->
        requestAddressBookAccess()
      ABAuthorizationStatus.Denied, ABAuthorizationStatus.Restricted -> {
        alert("Privacy Warning", "Permission was not granted for Contacts.", "OK")
      }
      else -> requestAddressBookAccess()
    }
  }

  /**
   * Prompt the user for access to their Address Book data
   */
  private fun requestAddressBookAccess() {
    addressBook?.requestAccess(object : ABAddressBook.RequestAccessCompletionHandler {
      // gotcha NSError?
      // IllegalArgumentException
      // Parameter specified as non-null is null: method kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull
      override fun requestAccess(granted: Boolean, error: NSError?) {
        if (granted) {
          DispatchQueue.getMainQueue().async(object : Runnable {
            override fun run() {
              //showPeoplePickerController()
              println("Authorized")
            }
          })
        } else {
          checkAddressBookAccess()
        }
      }
    })
  }

  /**
   * Called when users tap "Display Picker" in the application. Displays a
   * list of contacts and allows users to select a contact from that list. The
   * application only shows the phone, email, and birthdate information of the
   * selected contact.
   */
  private fun showPeoplePickerController() {
    val picker = ABPeoplePickerNavigationController()
    picker.peoplePickerDelegate = object : ABPeoplePickerNavigationControllerDelegateAdapter() {
      /**
       * Displays the information of a selected person.

       * @param peoplePicker
       * *
       * @param person
       * *
       * @return
       */
      override fun shouldContinueAfterSelectingPerson(peoplePicker: ABPeoplePickerNavigationController?,
                                                      person: ABPerson?): Boolean {
        return true
      }

      /**
       * Does not allow users to perform default actions such as dialing a
       * phone number, when they select a person property.
       */
      override fun shouldContinueAfterSelectingPerson(peoplePicker: ABPeoplePickerNavigationController?,
                                                      person: ABPerson?, property: ABProperty?, identifier: Int): Boolean {
        return false
      }

      /**
       * Dismisses the people picker and shows the application when users
       * tap Cancel.

       * @param peoplePicker
       */
      override fun didCancel(peoplePicker: ABPeoplePickerNavigationController?) {
        dismissViewController(true, null)
      }
    }
    // Display only a person's phone, email, and birthday
    val displayedItems = Arrays.asList(ABPersonProperty.Phone, ABPersonProperty.Email,
        ABPersonProperty.Birthday)
    picker.displayedProperties = displayedItems

    // Show the picker
    presentViewController(picker, true, null)
  }
}

