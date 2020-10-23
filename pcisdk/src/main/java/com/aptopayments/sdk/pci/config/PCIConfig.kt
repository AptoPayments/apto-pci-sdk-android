package com.aptopayments.sdk.pci.config

import com.google.gson.annotations.SerializedName

/**
 * @property configAuth [PCIConfigAuth] contains the configuration needed to authenticate.
 * @property theme [String] Optional. Name of the predefined theme that you are choosing.
 * @property configCard [PCIConfigCard] contains the card configuration.
 * @property style: [PCIConfigStyle] Contains the style configuration
 */
data class PCIConfig(
    @SerializedName("auth")
    val configAuth: PCIConfigAuth,
    @SerializedName("theme")
    val theme: String? = "dark",
    @SerializedName("values")
    val configCard: PCIConfigCard? = null,
    @Transient
    val style: PCIConfigStyle? = null
)
