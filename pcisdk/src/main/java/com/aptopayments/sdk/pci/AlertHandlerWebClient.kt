package com.aptopayments.sdk.pci

import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.aptopayments.sdk.pci.dialog.DialogFactory

internal class AlertHandlerWebClient(private val dialogFactory: DialogFactory) : WebChromeClient() {

    override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
        dialogFactory.getWrongCodeDialog().show(view.context, result)
        return true
    }

    override fun onJsPrompt(
        view: WebView,
        url: String,
        message: String,
        defaultValue: String,
        result: JsPromptResult
    ): Boolean {
        dialogFactory.getInputCodeDialog().show(view.context, defaultValue, result)
        return true
    }
}
