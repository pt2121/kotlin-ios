package com.prat.tablesearch

import org.robovm.apple.foundation.NSCoder
import org.robovm.apple.foundation.NSCodingAdapter
import org.robovm.apple.foundation.NSString

/**
 * Created by pt2121 on 11/27/15.
 */
class APLProduct : NSCodingAdapter {

  public val title: String
  public val hardwareType: String
  public val yearIntroduced: Int
  public val introPrice: Double

  constructor(coder: NSCoder) : super(coder) {
    title = coder.decodeString(NAME_KEY)
    hardwareType = coder.decodeString(TYPE_KEY)
    yearIntroduced = coder.decodeInteger(YEAR_KEY)
    introPrice = coder.decodeDouble(PRICE_KEY)
  }

  constructor(type: String, name: String, year: Int, price: Double) {
    this.hardwareType = type
    this.title = name
    this.yearIntroduced = year
    this.introPrice = price
  }

  override fun encode(coder: NSCoder?) {
    super.encode(coder)
    coder!!.encodeString(NAME_KEY, title)
    coder.encodeString(TYPE_KEY, hardwareType)
    coder.encodeInteger(YEAR_KEY, yearIntroduced)
    coder.encodeDouble(PRICE_KEY, introPrice)
  }

  companion object {
    private val NAME_KEY = "NameKey"
    private val TYPE_KEY = "TypeKey"
    private val YEAR_KEY = "YearKey"
    private val PRICE_KEY = "PriceKey"

    val deviceTypeTitle = NSString.getLocalizedString("Device")

    val desktopTypeTitle = NSString.getLocalizedString("Desktop")

    val portableTypeTitle = NSString.getLocalizedString("Portable")

    private var deviceTypeNames = listOf<String>(deviceTypeTitle, desktopTypeTitle, portableTypeTitle)

    private var deviceTypeDisplayNames = deviceTypeNames.map { typeName ->
      typeName to NSString.getLocalizedString(typeName)
    }.toMap()
  }

}