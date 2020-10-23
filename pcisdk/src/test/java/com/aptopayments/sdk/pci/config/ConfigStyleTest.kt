package com.aptopayments.sdk.pci.config

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class ConfigStyleTest {

    val gson = Gson()

    @Test
    fun `When no properties are set then empty json`() {
        val sut = PCIConfigStyleInternal()

        assertEquals("{}", transformToJson(sut))
    }

    @Test
    fun `When no createConfigWithTextColor then correct JSON generated`() {
        val color = "12aa34"
        val sut = PCIConfigStyleInternal.createConfigWithTextColor(color)

        assertEquals("{\"extends\":\"dark\",\"container\":{\"color\":\"#$color\"}}", transformToJson(sut))
    }

    private fun transformToJson(sut: PCIConfigStyleInternal) = gson.toJson(sut).toString()
}
