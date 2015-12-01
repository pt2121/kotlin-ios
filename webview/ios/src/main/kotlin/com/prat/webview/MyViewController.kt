package com.prat.webview

import org.robovm.apple.foundation.NSError
import org.robovm.apple.foundation.NSRange
import org.robovm.apple.foundation.NSURL
import org.robovm.apple.foundation.NSURLRequest
import org.robovm.apple.uikit.*
import org.robovm.objc.annotation.CustomClass
import org.robovm.objc.annotation.IBOutlet

@CustomClass("MyViewController")
class MyViewController : UIViewController(), UIWebViewDelegate, UITextFieldDelegate {
  @IBOutlet
  private val webView: UIWebView? = null
  @IBOutlet
  private val addressTextField: UITextField? = null

  override fun viewDidLoad() {
    super.viewDidLoad()

    configureWebView()
    loadAddressURL()
  }

  override fun viewWillDisappear(animated: Boolean) {
    super.viewWillDisappear(animated)

    UIApplication.getSharedApplication().isNetworkActivityIndicatorVisible = false
  }

  private fun loadAddressURL() {
    val requestURL = NSURL(addressTextField!!.text)
    val request = NSURLRequest(requestURL)
    webView!!.loadRequest(request)
  }

  private fun configureWebView() {
    webView!!.backgroundColor = UIColor.white()
    webView.isScalesPageToFit = true
    webView.dataDetectorTypes = UIDataDetectorTypes.All
  }

  override fun shouldStartLoad(webView: UIWebView, request: NSURLRequest, navigationType: UIWebViewNavigationType): Boolean {
    return true
  }

  override fun didStartLoad(webView: UIWebView) {
    UIApplication.getSharedApplication().isNetworkActivityIndicatorVisible = true
  }

  override fun didFinishLoad(webView: UIWebView) {
    UIApplication.getSharedApplication().isNetworkActivityIndicatorVisible = false
  }

  override fun didFailLoad(webView: UIWebView, error: NSError) {
    // Report the error inside the web view.
    val errorMessage = "An error occurred:"
    val errorFormatString = "<!doctype html><html><body><div style=\"width: 100%%; text-align: center; font-size: 36pt;\">%s%s</div></body></html>"

    val errorHTML = errorFormatString.format(errorMessage, error.localizedDescription)
    webView.loadHTML(errorHTML, null)

    UIApplication.getSharedApplication().isNetworkActivityIndicatorVisible = false
  }

  // This helps dismiss the keyboard when the "Done" button is clicked.
  override fun shouldReturn(textField: UITextField): Boolean {
    textField.resignFirstResponder()

    loadAddressURL()

    return true
  }

  override fun shouldBeginEditing(textField: UITextField): Boolean {
    return true
  }

  override fun didBeginEditing(textField: UITextField) {
  }

  override fun shouldEndEditing(textField: UITextField): Boolean {
    return true
  }

  override fun didEndEditing(textField: UITextField) {
  }

  override fun shouldChangeCharacters(textField: UITextField, range: NSRange, string: String): Boolean {
    return true
  }

  override fun shouldClear(textField: UITextField): Boolean {
    return true
  }
}

