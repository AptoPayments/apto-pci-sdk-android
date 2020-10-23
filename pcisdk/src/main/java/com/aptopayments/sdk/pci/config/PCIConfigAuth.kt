package com.aptopayments.sdk.pci.config

import com.google.gson.annotations.SerializedName

/**
 * @property cardId [String] The id of the card that is required to show data from
 * @property apiKey [String] The API-KEY obtained from Apto.
 * @property userToken [String] The current userToken obtained when the user completed the login.
 * @property environment The [PCIEnvironment] that the SDK will point to.
 */
data class PCIConfigAuth(
    @SerializedName("cardId")
    val cardId: String,
    @SerializedName("apiKey")
    val apiKey: String,
    @SerializedName("userToken")
    val userToken: String,
    @SerializedName("environment")
    val environment: PCIEnvironment
)
