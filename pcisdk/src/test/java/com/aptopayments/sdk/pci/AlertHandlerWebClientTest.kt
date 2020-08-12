package com.aptopayments.sdk.pci

import android.content.Context
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebView
import com.aptopayments.sdk.pci.dialog.DialogFactory
import com.aptopayments.sdk.pci.dialog.InputCodeDialog
import com.aptopayments.sdk.pci.dialog.WrongCodeDialog
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

private const val URL = "www.aptopayments.com"
private const val MESSAGE = "this is a message"
private const val DEFAULT_VALUE = "1"

class AlertHandlerWebClientTest {

    private val context: Context = mock()
    private val view: WebView = mock() {
        whenever(this.mock.context).thenReturn(context)
    }
    private val dialogFactory: DialogFactory = mock()
    private val sut = AlertHandlerWebClient(dialogFactory)

    @Test
    fun `when onJsAlert then wrongCodeDialog is shown`() {
        val dialog = mock<WrongCodeDialog>()
        val result: JsResult = mock()
        whenever(dialogFactory.getWrongCodeDialog()).thenReturn(dialog)

        sut.onJsAlert(view, URL, MESSAGE, result)

        verify(dialogFactory).getWrongCodeDialog()
        verify(dialog).show(context, result)
    }

    @Test
    fun `when onJsPrompt then InputCodeDialog is shown`() {
        val dialog = mock<InputCodeDialog>()
        val result: JsPromptResult = mock()
        whenever(dialogFactory.getInputCodeDialog()).thenReturn(dialog)

        sut.onJsPrompt(view, URL, MESSAGE, DEFAULT_VALUE, result)

        verify(dialogFactory).getInputCodeDialog()
        verify(dialog).show(context, DEFAULT_VALUE, result)
    }
}
