package com.aptopayments.sdk.pci

internal interface DialogStateRepository {
    fun isDialogShown(): Boolean
    fun dialogHidden()
    fun dialogShown()
}

internal object DialogStateRepositoryImpl : DialogStateRepository {

    private var dialogShown = false

    override fun isDialogShown() = dialogShown

    override fun dialogHidden() {
        dialogShown = false
    }

    override fun dialogShown() {
        dialogShown = true
    }
}
