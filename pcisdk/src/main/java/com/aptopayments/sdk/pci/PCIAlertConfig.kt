package com.aptopayments.sdk.pci

import android.content.Context

internal class PCIAlertConfig(
    var alertTexts: Map<String, String> = hashMapOf(),
    var alertButtonColors: Int = 0
) {

    fun getText(context: Context, id: String, defaultResource: Int) =
        alertTexts[id] ?: context.getString(defaultResource)
}
