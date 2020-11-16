package com.aptopayments.sdk.pci.dialog

import androidx.appcompat.app.AlertDialog
import com.aptopayments.sdk.pci.DialogStateRepository

internal abstract class BaseDialog(private val stateRepo: DialogStateRepository) {
    protected fun configureDialog(dialog: AlertDialog) {
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnCancelListener { stateRepo.dialogHidden() }
        dialog.setOnDismissListener { stateRepo.dialogHidden() }
        dialog.setOnShowListener { stateRepo.dialogShown() }
    }
}
