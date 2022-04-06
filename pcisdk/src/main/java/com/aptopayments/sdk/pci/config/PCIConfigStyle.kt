package com.aptopayments.sdk.pci.config

/**
 * @property textColor, Int, Represents the colors of the text in the card (Optional value).
 * @property labelPan Map<String,String>?, Style the PAN label (Optional value).
 * @property labelName Map<String,String>?, Style the Name label (Optional value).
 * @property labelCvv Map<String,String>?, Style the CVV label (Optional value).
 * @property labelExp Map<String,String>?, Style the Exp label (Optional value).
 */
data class PCIConfigStyle(
    val textColor: Int? = null,
    val labelPan: Map<String, String>? = null,
    val labelName: Map<String, String>? = null,
    val labelCvv: Map<String, String>? = null,
    val labelExp: Map<String, String>? = null,
    val otpSubmitButton: Map<String, String>? = null
)
