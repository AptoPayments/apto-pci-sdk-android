package com.aptopayments.sdk.pci.dialog

import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.aptopayments.sdk.pci.PCIAlertConfig
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

const val BUTTON_COLOR = 1
const val BUTTON_ID = 2

class AlertButtonStylizerTest {

    @Test
    fun `when style then correct color applied`() {
        val dialog: AlertDialog = mock()
        val button: Button = mock()
        val alertConfig = PCIAlertConfig(alertButtonColor = BUTTON_COLOR)
        val sut = AlertButtonStylizer(alertConfig)

        whenever(dialog.getButton(BUTTON_ID)).thenReturn(button)

        sut.style(dialog, BUTTON_ID)

        verify(button).setTextColor(BUTTON_COLOR)
    }
}
