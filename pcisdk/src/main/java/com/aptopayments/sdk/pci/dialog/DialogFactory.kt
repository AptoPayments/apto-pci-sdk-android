package com.aptopayments.sdk.pci.dialog

import com.aptopayments.sdk.pci.PCIAlertConfig

internal class DialogFactory(private val alertConfig: PCIAlertConfig, private val stylizer: AlertButtonStylizer) {

    fun getInputCodeDialog() = InputCodeDialog(alertConfig, stylizer)

    fun getWrongCodeDialog() = WrongCodeDialog(alertConfig, stylizer)
}
