package com.aptopayments.sdk.pci.config

import java.util.Locale

/**
 * This enum represent the different Apto Environments
 */
enum class PCIEnvironment {
    STG, SBX, PRD;

    companion object {
        fun fromString(value: String): PCIEnvironment {
            return try {
                valueOf(value.uppercase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                SBX
            }
        }
    }
}
