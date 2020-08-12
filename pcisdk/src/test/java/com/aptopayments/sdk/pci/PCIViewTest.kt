package com.aptopayments.sdk.pci

import android.webkit.WebView
import androidx.test.core.app.ApplicationProvider
import com.aptopayments.sdk.common.WebViewFake
import com.aptopayments.sdk.queue.WebViewJSActionsQueue
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class PCIViewTest {
    private lateinit var sut: PCIView
    private val webView = WebViewFake(ApplicationProvider.getApplicationContext())
    private val operationQueue = WebViewJSActionsQueueSpy(webView)

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
    fun `show cvv set to true call customiseUI with show cvv to true`() {
        // When
        sut.showCvv = true

        // Then
        assertTrue(operationQueue.addActionCalled)
        assertTrue(operationQueue.lastActionAdded!!.contains("\"showCvv\":true"))
    }

    @Test
    fun `show cvv set to false call customiseUI with show cvv to false`() {
        // When
        sut.showCvv = false

        // Then
        assertTrue(operationQueue.addActionCalled)
        assertTrue(operationQueue.lastActionAdded!!.contains("\"showCvv\":false"))
    }

    @Test
    fun `show exp set to true call customiseUI with show exp to true`() {
        // When
        sut.showExp = true

        // Then
        assertTrue(operationQueue.addActionCalled)
        assertTrue(operationQueue.lastActionAdded!!.contains("\"showExp\":true"))
    }

    @Test
    fun `show exp set to false call customiseUI with show exp to false`() {
        // When
        sut.showExp = false

        // Then
        assertTrue(operationQueue.addActionCalled)
        assertTrue(operationQueue.lastActionAdded!!.contains("\"showExp\":false"))
    }

    @Test
    fun `show pan set to true call customiseUI with show pan to true`() {
        // When
        sut.showPan = true

        // Then
        assertTrue(operationQueue.addActionCalled)
        assertTrue(operationQueue.lastActionAdded!!.contains("\"showPan\":true"))
    }

    @Test
    fun `show pan set to false call customiseUI with show pan to false`() {
        // When
        sut.showPan = false

        // Then
        assertTrue(operationQueue.addActionCalled)
        assertTrue(operationQueue.lastActionAdded!!.contains("\"showPan\":false"))
    }

    @Test
    fun `styles set call customiseUI`() {
        // When
        sut.styles = mapOf("font" to "Helvetica")

        // Then
        assertTrue(operationQueue.addActionCalled)
        assertTrue(operationQueue.lastActionAdded!!.contains("{\"font\":\"Helvetica\"}"))
    }

    @Test
    fun `initialise call initialise`() {
        // When
        sut.initialise("api", "token", "card_id", "last_four", "env")

        // Then
        assertTrue(operationQueue.addActionCalled)
        val expectedAction = "window.AptoPCISDK.initialise(\"api\", \"token\", \"card_id\", \"last_four\", \"env\")"
        assertEquals(expectedAction, operationQueue.lastActionAdded)
    }

    @Test
    fun `lastFour call lastFour`() {
        // When
        sut.lastFour()

        // Then
        assertTrue(operationQueue.addActionCalled)
        val expectedAction = "window.AptoPCISDK.lastFour()"
        assertEquals(expectedAction, operationQueue.lastActionAdded)
    }

    @Test
    fun `reveal call reveal`() {
        // When
        sut.reveal()

        // Then
        assertTrue(operationQueue.addActionCalled)
        val expectedAction = "window.AptoPCISDK.reveal()"
        assertEquals(expectedAction, operationQueue.lastActionAdded)
    }

    @Test
    fun `obfuscate call obfuscate`() {
        // When
        sut.obfuscate()

        // Then
        assertTrue(operationQueue.addActionCalled)
        val expectedAction = "window.AptoPCISDK.obfuscate()"
        assertEquals(expectedAction, operationQueue.lastActionAdded)
    }
}

private class WebViewJSActionsQueueSpy(webView: WebView) : WebViewJSActionsQueue(webView) {
    var addActionCalled = false
        private set
    var lastActionAdded: String? = null

    override fun addAction(action: String) {
        addActionCalled = true
        lastActionAdded = action
    }
}
