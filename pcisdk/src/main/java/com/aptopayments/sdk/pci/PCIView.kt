package com.aptopayments.sdk.pci

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.annotation.VisibleForTesting
import com.aptopayments.sdk.queue.WebViewJSActionsQueue
import org.json.JSONObject
import kotlin.properties.Delegates
import com.aptopayments.sdk.pci.AlertHandlerWebClient

private const val JS_PREFIX = "window.AptoPCISDK"

class PCIView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal lateinit var webView: WebView
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal lateinit var operationQueue: WebViewJSActionsQueue
    private var webViewClient : WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            operationQueue.isSuspended = false
        }
    }

    var styles: Map<String, Any> by Delegates.observable(mapOf()) { _, _ , _ ->
        customiseUI()
    }
    var showCvv: Boolean by Delegates.observable(true) { _, _ , _ ->
        customiseUI()
    }
    var showExp: Boolean by Delegates.observable(true) { _, _ , _ ->
        customiseUI()
    }
    var showPan: Boolean by Delegates.observable(true) { _, _ , _ ->
        customiseUI()
    }

    private fun customiseUI() {
        val flagsJSON = JSONObject(mapOf("showPan" to showPan, "showCvv" to showCvv, "showExp" to showExp))
        val stylesJSON = JSONObject(styles)
        operationQueue.addAction(action = "$JS_PREFIX.customiseUI($flagsJSON, $stylesJSON)")
    }

    init {
        View.inflate(context, R.layout.view_pci_view, this)
        setUpWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webView = findViewById(R.id.wv_web_view)
        operationQueue = WebViewJSActionsQueue(webView)
        webView.webViewClient = webViewClient
        webView.webChromeClient = AlertHandlerWebClient()
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.loadUrl("file:///android_asset/container.html")
    }

    fun initialise(apiKey: String, userToken: String, cardId: String, lastFour: String, environment: String) =
            operationQueue.addAction("$JS_PREFIX.initialise(\"$apiKey\", \"$userToken\", \"$cardId\", \"$lastFour\", \"$environment\")")

    fun lastFour() = operationQueue.addAction("$JS_PREFIX.lastFour()")

    fun reveal() = operationQueue.addAction("$JS_PREFIX.reveal()")

    fun obfuscate() = operationQueue.addAction("$JS_PREFIX.obfuscate()")
}
