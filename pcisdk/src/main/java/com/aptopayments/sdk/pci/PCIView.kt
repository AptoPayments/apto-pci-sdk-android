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
import com.aptopayments.sdk.pci.config.PCIConfig
import com.aptopayments.sdk.pci.config.PCIConfigStyle
import com.aptopayments.sdk.pci.config.PCIConfigStyleInternal
import com.aptopayments.sdk.pci.dialog.AlertButtonStylizer
import com.aptopayments.sdk.pci.dialog.DialogFactory
import com.aptopayments.sdk.queue.WebViewJSActionsQueue
import com.google.gson.Gson

private const val JS_PREFIX = "window.AptoPCISdk"
private const val CONTAINER_LOCAL_URL = "file:///android_asset/container.html"

class PCIView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal lateinit var webView: WebView

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal lateinit var operationQueue: WebViewJSActionsQueue

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var gson = Gson()

    private val webViewClient: WebViewClient = createWebViewClient()
    private val alertConfig by lazy { PCIAlertConfig(alertButtonColors = getColorAccent(context)) }
    private val alertHandlerWebClient = createAlertHandlerWebClient()

    init {
        View.inflate(context, R.layout.view_pci_view, this)
        setUpWebView()
    }

    fun init(config: PCIConfig) {
        initPCIView(config)
        setStyle(config.style)
    }

    fun showPCIData() = sendActionToJs("$JS_PREFIX.showPCIData()")

    fun hidePCIData() = sendActionToJs("$JS_PREFIX.hidePCIData()")

    private fun initPCIView(config: PCIConfig) {
        sendActionToJs("$JS_PREFIX.init(${toJson(config)})")
    }

    fun setStyle(value: PCIConfigStyle?) {
        value?.let {
            it.alertButtonColor?.let { color -> alertConfig.alertButtonColors = color }
            setTextColor(it)
        }
    }

    private fun setTextColor(style: PCIConfigStyle) {
        style.textColor?.let { intColor ->
            val hexColor = getHexColorFromInt(intColor)
            val json = toJson(PCIConfigStyleInternal.createConfigWithTextColor(hexColor))
            sendActionToJs("$JS_PREFIX.setStyle($json)")
        }
    }

    private fun getHexColorFromInt(intColor: Int) = String.format("#%06X", 0xFFFFFF and intColor)

    private fun sendActionToJs(action: String) {
        operationQueue.addAction(action)
    }

    private fun createAlertHandlerWebClient() =
        AlertHandlerWebClient(DialogFactory(AlertButtonStylizer(alertConfig)))

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

    private fun createWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                operationQueue.isSuspended = false
            }
        }
    }

    private fun toJson(config: Any) = gson.toJson(config)
}
