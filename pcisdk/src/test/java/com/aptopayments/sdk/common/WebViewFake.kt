package com.aptopayments.sdk.common

import android.content.Context
import android.webkit.ValueCallback

internal class WebViewFake(context: Context) : WebViewSpy(context) {
    override fun loadUrl(url: String) {
        webViewClient.onPageFinished(this, url)
    }

    override fun evaluateJavascript(script: String, resultCallback: ValueCallback<String>?) {
        super.evaluateJavascript(script, resultCallback)
        resultCallback?.onReceiveValue(script)
    }
}
