package com.aptopayments.sdk.pci.dialog

import android.content.Context
import android.webkit.JsResult
import androidx.appcompat.app.AlertDialog
import com.aptopayments.sdk.pci.R

internal class WrongCodeDialog(
    private val buttonStylizer: AlertButtonStylizer
) {

    fun show(context: Context, result: JsResult) {
        val dialog = AlertDialog.Builder(context)
            .setMessage(getMessage(context))
            .setPositiveButton(getOkButtonTitle(context)) { _, _ -> result.confirm() }
            .create()
        dialog.show()

        buttonStylizer.style(dialog, AlertDialog.BUTTON_POSITIVE)
    }

    private fun getOkButtonTitle(context: Context) = context.getString(R.string.pcisdk_wrong_code_ok)

    private fun getMessage(context: Context) = context.getString(R.string.pcisdk_wrong_code_message)
}
