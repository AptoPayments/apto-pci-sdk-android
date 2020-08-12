package com.aptopayments.sdk.pci.dialog

import androidx.appcompat.app.AlertDialog
import com.aptopayments.sdk.pci.PCIAlertConfig

internal class AlertButtonStylizer(private val alertConfig: PCIAlertConfig) {

    fun style(dialog: AlertDialog, buttonId: Int) {
        val button = dialog.getButton(buttonId)
        button.setTextColor(alertConfig.alertButtonColors)
    }
}
