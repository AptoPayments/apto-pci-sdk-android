package com.aptopayments.sdk.pci.dialog

import android.content.Context
import android.text.InputType
import android.webkit.JsPromptResult
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.aptopayments.sdk.pci.DialogStateRepository
import com.aptopayments.sdk.pci.R

internal class InputCodeDialog(
    private val buttonStylizer: AlertButtonStylizer,
    stateRepository: DialogStateRepository
) : BaseDialog(stateRepository) {

    fun show(context: Context, defaultValue: String, result: JsPromptResult) {
        val input = getInput(context, defaultValue)
        val dialog = AlertDialog.Builder(context)
            .setTitle("")
            .setView(input)
            .setMessage(getMessage(context))
            .setPositiveButton(getOkButtonTitle(context)) { _, _ -> result.confirm(getInput(input)) }
            .setNegativeButton(getCancelButtonTitle(context)) { _, _ -> result.cancel() }
            .create()
        configureDialog(dialog)
        dialog.show()
        changeButtonsColor(dialog)
    }

    private fun getInput(input: EditText) = if (input.text.toString().isNotEmpty()) input.text.toString() else " "

    private fun getMessage(context: Context) = context.getString(R.string.pcisdk_input_code_message)

    private fun getInput(context: Context, defaultValue: String): EditText {
        return EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(defaultValue)
        }
    }

    private fun getCancelButtonTitle(context: Context) = context.getString(R.string.pcisdk_input_code_cancel)

    private fun getOkButtonTitle(context: Context) = context.getString(R.string.pcisdk_input_code_ok)

    private fun changeButtonsColor(dialog: AlertDialog) {
        buttonStylizer.style(dialog, AlertDialog.BUTTON_NEGATIVE)
        buttonStylizer.style(dialog, AlertDialog.BUTTON_POSITIVE)
    }
}
