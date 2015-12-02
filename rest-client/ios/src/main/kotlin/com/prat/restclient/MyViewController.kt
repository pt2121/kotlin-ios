package com.prat.restclient

import org.robovm.apple.uikit.UILabel
import org.robovm.apple.uikit.UIViewController
import org.robovm.objc.annotation.CustomClass
import org.robovm.objc.annotation.IBAction
import org.robovm.objc.annotation.IBOutlet

@CustomClass("MyViewController")
class MyViewController : UIViewController() {
  private val counterStore = CounterStore()

  @IBOutlet
  private val label: UILabel? = null

  @IBAction
  private fun clicked() {
    counterStore.add(1)
    label!!.text = "Click Nr. " + counterStore.get()
    WebService.newInstance().repo().subscribe({
      println("${it.name} : ${it.description}")
    }, {
      println(it.message)
    })
  }
}

