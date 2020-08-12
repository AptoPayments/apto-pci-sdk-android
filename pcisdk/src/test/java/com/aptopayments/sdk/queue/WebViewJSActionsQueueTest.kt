package com.aptopayments.sdk.queue

import androidx.test.core.app.ApplicationProvider
import com.aptopayments.sdk.common.WebViewFake
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class WebViewJSActionsQueueTest {
    private lateinit var sut: WebViewJSActionsQueue

    // Collaborators
    private val webView = WebViewFake(ApplicationProvider.getApplicationContext())

    @Before
    fun setUp() {
        sut = WebViewJSActionsQueue(webView)
    }

    @Test
    fun `add action in suspended state do not evaluate javascript`() {
        // Given
        val action = "javascript.action"

        // When
        sut.addAction(action)

        // Then
        assertFalse(webView.evaluateJavascriptCalled)
    }

    @Test
    fun `add action in not suspended state evaluate javascript`() {
        // Given
        val action = "javascript.action"
        sut.isSuspended = false

        // When
        sut.addAction(action)

        // Then
        assertTrue(webView.evaluateJavascriptCalled)
    }

    @Test
    fun `actions are evaluated when suspended state changes`() {
        // Given
        val action = "javascript.action"
        sut.addAction(action)
        sut.addAction(action)

        // When
        sut.isSuspended = false

        // Then
        assertTrue(webView.evaluateJavascriptCalled)
        assertEquals(2, webView.evaluateJavascriptCallsCount)
    }

    @Test
    fun `actions are not executed twice`() {
        // Given
        val action = "javascript.action"
        sut.addAction(action)
        sut.isSuspended = false

        // When
        sut.isSuspended = true
        sut.isSuspended = false

        // Then
        assertTrue(webView.evaluateJavascriptCalled)
        assertEquals(1, webView.evaluateJavascriptCallsCount)
    }
}
