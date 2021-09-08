package com.aptopayments.sdk.pci

import androidx.test.core.app.ApplicationProvider
import com.aptopayments.sdk.common.WebViewFake
import com.aptopayments.sdk.pci.config.PCIConfig
import com.aptopayments.sdk.queue.WebViewJSActionsQueue
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class PCIViewTest {
    private lateinit var sut: PCIView
    private val webView = WebViewFake(ApplicationProvider.getApplicationContext())
    private val operationQueue = mock<WebViewJSActionsQueue>()

    @Before
    fun setUp() {
        sut = PCIView(ApplicationProvider.getApplicationContext())
        sut.operationQueue = operationQueue
        swapWebView()
    }

    private fun swapWebView() {
        webView.webViewClient = sut.webView.webViewClient
        sut.webView = webView
        webView.loadUrl("")
    }

    @Test
    fun `whenever init called then init in Js is called with correct parameters`() {
        val json = "THIS IS A JSON"
        val config: PCIConfig = mock()
        val gson: Gson = mock()
        sut.gson = gson
        whenever(gson.toJson(config)).thenReturn(json)

        sut.init(config)

        val expectedAction = "window.AptoPCISdk.init($json)"
        verify(operationQueue).addAction(expectedAction)
    }

    @Test
    fun `whenever showPCIData called then showPCIData in Js is called`() {
        sut.showPCIData()

        val expectedAction = "window.AptoPCISdk.showPCIData()"
        verify(operationQueue).addAction(expectedAction)
    }
}
