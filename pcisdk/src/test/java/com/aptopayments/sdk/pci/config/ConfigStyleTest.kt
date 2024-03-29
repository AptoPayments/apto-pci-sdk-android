package com.aptopayments.sdk.pci.config

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

private const val COLOR = "12aa34"

class ConfigStyleTest {

    private val exampleMap = mapOf("test" to "result")
    private val jsonExampleMap = "{\"test\":\"result\"}"
    private val gson = Gson()

    @Test
    fun `When no properties are set then empty json`() {
        val sut = PCIConfigStyleInternal()

        assertEquals("{}", transformToJson(sut))
    }

    @Test
    fun `When only color provided then correct JSON generated`() {
        val sut = PCIConfigStyleInternal.createConfig(textColor = COLOR, style = null, theme = null)

        assertEquals("{\"extends\":\"dark\",\"container\":{\"color\":\"#$COLOR\"}}", transformToJson(sut))
    }

    @Test
    fun `When labelPan provided then is included in JSON`() {
        val sut = PCIConfigStyleInternal.createConfig(COLOR, PCIConfigStyle(labelPan = exampleMap), null)

        assertEquals("{\"extends\":\"dark\",\"container\":{\"color\":\"#$COLOR\"},\"labelPan\":$jsonExampleMap}", transformToJson(sut))
    }

    @Test
    fun `When theme light provided then is included in JSON`() {
        val sut = PCIConfigStyleInternal.createConfig(COLOR, null, "light")

        assertEquals("{\"extends\":\"light\",\"container\":{\"color\":\"#$COLOR\"}}", transformToJson(sut))
    }

    @Test
    fun `When labelName provided then is included in JSON`() {
        val sut = PCIConfigStyleInternal.createConfig(COLOR, PCIConfigStyle(labelName = exampleMap), null)

        assertEquals("{\"extends\":\"dark\",\"container\":{\"color\":\"#$COLOR\"},\"labelName\":$jsonExampleMap}", transformToJson(sut))
    }

    @Test
    fun `When labelExp provided then is included in JSON`() {
        val sut = PCIConfigStyleInternal.createConfig(COLOR, PCIConfigStyle(labelExp = exampleMap), null)

        assertEquals("{\"extends\":\"dark\",\"container\":{\"color\":\"#$COLOR\"},\"labelExp\":$jsonExampleMap}", transformToJson(sut))
    }

    @Test
    fun `When labelCvv provided then is included in JSON`() {
        val sut = PCIConfigStyleInternal.createConfig(COLOR, PCIConfigStyle(labelCvv = exampleMap), null)

        assertEquals("{\"extends\":\"dark\",\"container\":{\"color\":\"#$COLOR\"},\"labelCvv\":$jsonExampleMap}", transformToJson(sut))
    }

    @Test
    fun `When otbSubmitButton provided then is included in JSON`() {
        val sut = PCIConfigStyleInternal.createConfig(COLOR, PCIConfigStyle(otpSubmitButton = exampleMap), null)

        assertEquals("{\"extends\":\"dark\",\"container\":{\"color\":\"#$COLOR\"},\"inlineForm\":{\"submit\":$jsonExampleMap}}", transformToJson(sut))
    }

    private fun transformToJson(sut: PCIConfigStyleInternal) = gson.toJson(sut).toString()
}
