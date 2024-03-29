package com.aptopayments.sdk.pci

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.VisibleForTesting
import com.aptopayments.sdk.pci.config.PCIConfig
import com.aptopayments.sdk.pci.config.PCIConfigStyle
import com.aptopayments.sdk.pci.config.PCIConfigStyleInternal
import com.aptopayments.sdk.queue.WebViewJSActionsQueue
import com.google.gson.Gson

private const val JS_PREFIX = "window.AptoPCISdk"
private const val CONTAINER_LOCAL_URL = "file:///android_asset/container.html"
private const val HEX_MASK = 0xFFFFFF

class PCIView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal lateinit var webView: WebView

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal lateinit var operationQueue: WebViewJSActionsQueue

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal var gson = Gson()

    private val webViewClient: WebViewClient = createWebViewClient()

    init {
        View.inflate(context, R.layout.view_pci_view, this)
        setUpWebView()
    }

    fun init(config: PCIConfig) {
        initPCIView(config)
        config.style?.let { setStyle(it, config.theme) }
    }

    fun showPCIData() = sendActionToJs("$JS_PREFIX.showPCIData()")

    fun hidePCIData() {
        sendActionToJs("$JS_PREFIX.hidePCIData()")
    }

    fun showSetPinForm() {
        sendActionToJs("$JS_PREFIX.showSetPinForm()")
    }

    private fun initPCIView(config: PCIConfig) {
        sendActionToJs("$JS_PREFIX.init(${toJson(config)})")
    }

    fun setStyle(style: PCIConfigStyle, theme: String? = null) {
        val hexColor = getHexColorFromInt(getCardTextColor(style))
        val json = toJson(PCIConfigStyleInternal.createConfig(hexColor, style, theme))
        sendActionToJs("$JS_PREFIX.setStyle($json)")
    }

    private fun getCardTextColor(style: PCIConfigStyle?) =
        style?.textColor ?: getColorFromResources(R.color.pcisdk_text_color)

    private fun getColorFromResources(@ColorRes id: Int) = resources.getColor(id, null)

    private fun getHexColorFromInt(intColor: Int) = String.format("%06X", HEX_MASK and intColor)

    private fun sendActionToJs(action: String) {
        operationQueue.addAction(action)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {
        webView = findViewById(R.id.wv_web_view)
        operationQueue = WebViewJSActionsQueue(webView)
        webView.webViewClient = webViewClient
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.loadUrl(CONTAINER_LOCAL_URL)
    }

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
