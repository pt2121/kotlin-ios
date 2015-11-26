package com.prt2121.kitty

/**
 * Created by pt2121 on 11/25/15.
 */
object PhoneTranslator {
  fun toNumber(raw: String?): String {
    if (raw.isNullOrBlank()) {
      return ""
    } else {
      return raw!!.toUpperCase().fold("", {
        acc, c ->
        if (" -0123456789".contains(c)) {
          acc.plus(c)
        } else {
          //acc.plus(c.toNumber())
          acc.concat(toNumber(c))
        }
      })
    }
  }

  fun toNumber(c : Char): String {
    when {
      "ABC".contains(c) -> return "2"
      "DEF".contains(c) -> return "3"
      "GHI".contains(c) -> return "4"
      "JKL".contains(c) -> return "5"
      "MNO".contains(c) -> return "6"
      "PQRS".contains(c) -> return "7"
      "TUV".contains(c) -> return "8"
      "WXYZ".contains(c) -> return "9"
      else -> return ""
    }
  }

//  fun Char.toNumber(): String {
//    when {
//      "ABC".contains(this) -> return "2"
//      "DEF".contains(this) -> return "3"
//      "GHI".contains(this) -> return "4"
//      "JKL".contains(this) -> return "5"
//      "MNO".contains(this) -> return "6"
//      "PQRS".contains(this) -> return "7"
//      "TUV".contains(this) -> return "8"
//      "WXYZ".contains(this) -> return "9"
//      else -> return ""
//    }
//  }
}