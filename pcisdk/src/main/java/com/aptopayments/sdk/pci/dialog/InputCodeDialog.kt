package com.aptopayments.sdk.pci.dialog

import android.content.Context
import android.text.InputType
import android.webkit.JsPromptResult
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.aptopayments.sdk.pci.PCIAlertConfig
import com.aptopayments.sdk.pci.R

internal class InputCodeDialog(
    private val alertConfig: PCIAlertConfig,
    private val buttonStylizer: AlertButtonStylizer
) {

    fun show(context: Context, defaultValue: String, result: JsPromptResult) {
        val input = getInput(context, defaultValue)
        val dialog = AlertDialog.Builder(context)
            .setTitle("")
            .setView(input)
            .setMessage(getMessage(context))
            .setPositiveButton(getOkButtonTitle(context)) { _, _ -> result.confirm(input.text.toString()) }
            .setNegativeButton(getCancelButtonTitle(context)) { _, _ -> result.cancel() }
            .create()
        dialog.show()
        changeButtonsColor(dialog)
    }

    private fun getMessage(context: Context) =
        alertConfig.getText(context, "inputCode.message",
            R.string.pcisdk_input_code_message
        )

    private fun getInput(context: Context, defaultValue: String): EditText {
        return EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(defaultValue)
        }
    }

    private fun getCancelButtonTitle(context: Context) =
        alertConfig.getText(context, "inputCode.cancelAction",
            R.string.pcisdk_input_code_cancel
        )

    private fun getOkButtonTitle(context: Context) =
        alertConfig.getText(context, "inputCode.okAction",
            R.string.pcisdk_input_code_ok
        )

    private fun changeButtonsColor(dialog: AlertDialog) {
        buttonStylizer.style(dialog, androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
        buttonStylizer.style(dialog, androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
    }
}
