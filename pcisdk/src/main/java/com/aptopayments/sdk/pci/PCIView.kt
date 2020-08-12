package com.aptopayments.sdk.pci

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.annotation.VisibleForTesting
import com.aptopayments.sdk.pci.dialog.AlertButtonStylizer
import com.aptopayments.sdk.pci.dialog.DialogFactory
import com.aptopayments.sdk.queue.WebViewJSActionsQueue
import kotlin.properties.Delegates
import org.json.JSONObject

private const val JS_PREFIX = "window.AptoPCISDK"
private const val CONTAINER_LOCAL_URL = "file:///android_asset/container.html"

class PCIView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal lateinit var webView: WebView

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal lateinit var operationQueue: WebViewJSActionsQueue
    private val webViewClient: WebViewClient = createWebViewClient()
    private val alertConfig by lazy { PCIAlertConfig(alertButtonColors = getColorAccent(context)) }
    private val alertHandlerWebClient = createAlertHandlerWebClient()

    var styles: Map<String, Any> by Delegates.observable(mapOf()) { _, _, _ ->
        customiseUI()
    }
    var showCvv: Boolean by Delegates.observable(true) { _, _, _ ->
        customiseUI()
    }
    var showExp: Boolean by Delegates.observable(true) { _, _, _ ->
        customiseUI()
    }
    var showPan: Boolean by Delegates.observable(true) { _, _, _ ->
        customiseUI()
    }
    var alertTexts: Map<String, String> by Delegates.observable(mapOf()) { _, _, newValue ->
        alertConfig.alertTexts = newValue
    }

    var alertButtonColor: Int by Delegates.observable(getColorAccent(context)) { _, _, newValue ->
        alertConfig.alertButtonColors = newValue
    }

    init {
        View.inflate(context, R.layout.view_pci_view, this)
        setUpWebView()
    }

    fun initialise(apiKey: String, userToken: String, cardId: String, lastFour: String, environment: String) =
        operationQueue.addAction("$JS_PREFIX.initialise(\"$apiKey\", \"$userToken\", \"$cardId\", \"$lastFour\", \"$environment\")")

    fun lastFour() = operationQueue.addAction("$JS_PREFIX.lastFour()")

    fun reveal() = operationQueue.addAction("$JS_PREFIX.reveal()")

    fun obfuscate() = operationQueue.addAction("$JS_PREFIX.obfuscate()")

    private fun createAlertHandlerWebClient() =
        AlertHandlerWebClient(DialogFactory(alertConfig, AlertButtonStylizer(alertConfig)))

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webView = findViewById(R.id.wv_web_view)
        operationQueue = WebViewJSActionsQueue(webView)
        webView.webViewClient = webViewClient
        webView.webChromeClient = alertHandlerWebClient
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.loadUrl(CONTAINER_LOCAL_URL)
    }

    private fun getColorAccent(context: Context) = ColorHelper().getColorAccent(context)

    private fun customiseUI() {
        val flagsJSON = JSONObject(mapOf("showPan" to showPan, "showCvv" to showCvv, "showExp" to showExp))
        val stylesJSON = JSONObject(styles)
        operationQueue.addAction(action = "$JS_PREFIX.customiseUI('$flagsJSON', '$stylesJSON')")
    }

    private fun createWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                operationQueue.isSuspended = false
            }
        }
    }
}
