package com.aptopayments.sdk.pci.dialog

import com.aptopayments.sdk.pci.DialogStateRepository

internal class DialogFactory(private val stylizer: AlertButtonStylizer, private val stateRepo: DialogStateRepository) {

    fun getInputCodeDialog() = InputCodeDialog(stylizer, stateRepo)

    fun getWrongCodeDialog() = WrongCodeDialog(stylizer, stateRepo)
}
