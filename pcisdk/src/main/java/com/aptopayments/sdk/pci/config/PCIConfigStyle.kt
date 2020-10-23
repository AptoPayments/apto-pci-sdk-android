package com.aptopayments.sdk.pci.config

/**
 * @property textColor, Int, Represents the colors of the text in the card (Optional value).
 * @property alertButtonColor Int, Represents the color of the Alert OTP texts (Optional value).
 */
data class PCIConfigStyle(
    val textColor: Int? = null,
    val alertButtonColor: Int? = null
)
