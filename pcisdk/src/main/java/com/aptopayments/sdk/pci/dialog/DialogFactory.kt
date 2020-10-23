package com.aptopayments.sdk.pci.dialog

internal class DialogFactory(private val stylizer: AlertButtonStylizer) {

    fun getInputCodeDialog() = InputCodeDialog(stylizer)

    fun getWrongCodeDialog() = WrongCodeDialog(stylizer)
}
