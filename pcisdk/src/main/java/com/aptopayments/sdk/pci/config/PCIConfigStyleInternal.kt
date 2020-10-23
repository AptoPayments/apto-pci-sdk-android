package com.aptopayments.sdk.pci.config

import com.google.gson.annotations.SerializedName

internal data class PCIConfigStyleInternal(
    @SerializedName("extends")
    val extends: String? = null,
    @SerializedName("container")
    val container: Map<String, String>? = null
) {
    companion object {
        fun createConfigWithTextColor(textColor: String): PCIConfigStyleInternal {
            return PCIConfigStyleInternal(extends = "dark", container = mapOf("color" to "#$textColor"))
        }
    }
}
