package com.aptopayments.sdk.pci

import android.app.AlertDialog
import android.text.InputType
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebView
import android.widget.EditText
import android.webkit.WebChromeClient

internal class AlertHandlerWebClient : WebChromeClient() {
    override fun onJsAlert(
        view: WebView,
        url: String,
        message: String,
        result: JsResult
    ): Boolean {
        val builder = AlertDialog.Builder(view.context)
        builder.setMessage(message)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, which -> result.confirm() }
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, which -> result.cancel() }
            .create()
            .show()
        return true
    }
    override fun onJsPrompt(
        view: WebView,
        url: String,
        message: String,
        defaultValue: String,
        result: JsPromptResult
    ): Boolean {
        val input = EditText(view.context)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.setText(defaultValue)
        AlertDialog.Builder(view.context)
            .setTitle("")
            .setView(input)
            .setMessage(message)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, which -> result.confirm(input.text.toString()) }
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, which -> result.cancel() }
            .create()
            .show()
        return true
    }
}
