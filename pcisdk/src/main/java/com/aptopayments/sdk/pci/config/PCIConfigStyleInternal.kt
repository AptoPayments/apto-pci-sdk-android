package com.aptopayments.sdk.pci.config

import com.google.gson.annotations.SerializedName

internal data class PCIConfigStyleInternal(
    @SerializedName("extends")
    val extends: String? = null,
    @SerializedName("container")
    val container: Map<String, String>? = null,
    @SerializedName("labelPan")
    val labelPan: Map<String, String>? = null,
    @SerializedName("labelCvv")
    val labelCvv: Map<String, String>? = null,
    @SerializedName("labelExp")
    val labelExp: Map<String, String>? = null,
    @SerializedName("labelName")
    val labelName: Map<String, String>? = null
) {
    companion object {
        fun createConfig(
            textColor: String,
            style: PCIConfigStyle?,
            theme: String?
        ): PCIConfigStyleInternal {
            return PCIConfigStyleInternal(
                extends = theme ?: "dark",
                container = mapOf("color" to "#$textColor"),
                labelPan = style?.labelPan,
                labelCvv = style?.labelCvv,
                labelExp = style?.labelExp,
                labelName = style?.labelName
            )
        }
    }
}
