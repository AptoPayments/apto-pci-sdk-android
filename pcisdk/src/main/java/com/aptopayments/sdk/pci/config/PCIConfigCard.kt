package com.aptopayments.sdk.pci.config

import com.google.gson.annotations.SerializedName

/**
 * @property lastFour String. Sets the last four numbers of the card. If not present "****" is displayed (Optional parameter).
 * @property labelPan String. The default value for this label is empty (Optional parameter).
 * @property labelCvv String. The default value for this label is "CVV" (Optional parameter).
 * @property labelExp String. The default value for this label is "EXP" (Optional parameter).
 * @property labelName String The default value for this label is empty (Optional parameter).
 * @property nameOnCard String. The name that will be displayed in the card (Optional parameter).
 */
data class PCIConfigCard(
    @SerializedName("lastFour")
    val lastFour: String? = null,
    @SerializedName("labelPan")
    val labelPan: String? = null,
    @SerializedName("labelCvv")
    val labelCvv: String? = null,
    @SerializedName("labelExp")
    val labelExp: String? = null,
    @SerializedName("labelName")
    val labelName: String? = null,
    @SerializedName("nameOnCard")
    val nameOnCard: String? = null
)
