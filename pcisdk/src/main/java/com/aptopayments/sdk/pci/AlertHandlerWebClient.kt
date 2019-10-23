package com.aptopayments.sdk.pci

import android.app.AlertDialog
import android.text.InputType
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebView
import android.widget.EditText
import android.webkit.WebChromeClient

internal class AlertHandlerWebClient : WebChromeClient() {
    var alertTexts: Map<String, String> = hashMapOf()

    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        val context = view.context
        val okButtonTitle = alertTexts["wrongCode.okAction"] ?: context.getString(R.string.wrong_code_ok)
        val msg = alertTexts["wrongCode.message"] ?: context.getString(R.string.wrong_code_message)
        AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(okButtonTitle) { _, _ -> result.confirm() }
                .create()
                .show()
        return true
    }

    override fun onJsPrompt(view: WebView, url: String, message: String, defaultValue: String,
                            result: JsPromptResult): Boolean {
        val context = view.context
        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.setText(defaultValue)
        val okButtonTitle = alertTexts["inputCode.okAction"] ?: context.getString(R.string.input_code_ok)
        val cancelButtonTitle = alertTexts["inputCode.cancelAction"] ?: context.getString(R.string.input_code_cancel)
        val msg = alertTexts["inputCode.message"] ?: context.getString(R.string.input_code_message)
        AlertDialog.Builder(context)
                .setTitle("")
                .setView(input)
                .setMessage(msg)
                .setPositiveButton(okButtonTitle) { _, _ -> result.confirm(input.text.toString()) }
                .setNegativeButton(cancelButtonTitle) { _, _ -> result.cancel() }
                .create()
                .show()
        return true
    }
}
