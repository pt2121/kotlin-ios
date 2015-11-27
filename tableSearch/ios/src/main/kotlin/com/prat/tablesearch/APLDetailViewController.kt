package com.prat.tablesearch

/**
 * Created by pt2121 on 11/27/15.
 */
import org.robovm.apple.foundation.NSCoder
import org.robovm.apple.foundation.NSNumber
import org.robovm.apple.foundation.NSNumberFormatter
import org.robovm.apple.foundation.NSNumberFormatterStyle
import org.robovm.apple.uikit.UILabel
import org.robovm.apple.uikit.UIViewController
import org.robovm.objc.annotation.CustomClass
import org.robovm.objc.annotation.IBOutlet

@CustomClass("APLDetailViewController")
class APLDetailViewController : UIViewController() {

  private var product: APLProduct? = null
  @IBOutlet
  private val yearLabel: UILabel? = null
  @IBOutlet
  private val priceLabel: UILabel? = null

  override fun viewWillAppear(animated: Boolean) {
    super.viewWillAppear(animated)
    title = product!!.title

    yearLabel!!.text = product!!.yearIntroduced.toString()

    val numFormatter = NSNumberFormatter()
    numFormatter.numberStyle = NSNumberFormatterStyle.Currency
    val priceString = numFormatter.format(NSNumber.valueOf(product!!.introPrice))
    priceLabel!!.text = priceString
  }

  fun setProduct(product: APLProduct) {
    this.product = product
  }

  override fun encodeRestorableState(coder: NSCoder) {
    super.encodeRestorableState(coder)

    // encode the product
    coder.encodeObject(VIEW_CONTROLLER_PRODUCT_KEY, product)
  }

  override fun decodeRestorableState(coder: NSCoder) {
    super.decodeRestorableState(coder)

    // restore the product
    product = coder.decodeObject(VIEW_CONTROLLER_PRODUCT_KEY, APLProduct::class.java)
  }

  companion object {
    private val VIEW_CONTROLLER_PRODUCT_KEY = "ViewControllerProductKey"
  }
}