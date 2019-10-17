package com.aptopayments.sdk.common

import android.content.Context
import android.webkit.ValueCallback
import android.webkit.WebView

internal open class WebViewSpy(context: Context) : WebView(context) {
    var evaluateJavascriptCalled = false
        private set
    var evaluateJavascriptCallsCount = 0

    override fun evaluateJavascript(script: String?, resultCallback: ValueCallback<String>?) {
        super.evaluateJavascript(script, resultCallback)
        evaluateJavascriptCalled = true
        evaluateJavascriptCallsCount += 1
    }
}
