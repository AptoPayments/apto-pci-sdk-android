package com.aptopayments.sdk.pci

import org.junit.After
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DialogStateRepositoryImplTest {

    private val sut: DialogStateRepository = DialogStateRepositoryImpl

    @Test
    fun `when nothing changed then isDialogShown is false`() {
        assertFalse(sut.isDialogShown())
    }

    @Test
    fun `when dialogHidden then isDialogShown is false`() {
        sut.dialogHidden()

        assertFalse(sut.isDialogShown())
    }

    @Test
    fun `when dialogShown then isDialogShown is true`() {
        sut.dialogShown()

        assertTrue(sut.isDialogShown())
    }

    @After
    fun tearDown() {
        sut.dialogHidden()
    }
}
