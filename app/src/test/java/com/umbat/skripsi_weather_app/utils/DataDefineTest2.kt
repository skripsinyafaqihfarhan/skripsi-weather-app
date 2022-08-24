package com.umbat.skripsi_weather_app.utils

import android.provider.ContactsContract
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.remote.Scan
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class DataDefineTest2 {
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule val mainDispatcherRule = MainDispatcherRule()
    private lateinit var dataDefine: DataDefine
    @Before fun setUp() { dataDefine = DataDefine() }

    @Test fun `arahAngin jalur 1`() = runTest {
        val expected = "Utara"
        val actual = dataDefine.arahAngin("N")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 2`() = runTest {
        val expected = "Utara-Timur Laut"
        val actual = dataDefine.arahAngin("NNE")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 3`() = runTest {
        val expected = "Timur Laut"
        val actual = dataDefine.arahAngin("NE")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 4`() = runTest {
        val expected = "Timur-Timur Laut"
        val actual = dataDefine.arahAngin("ENE")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 5`() = runTest {
        val expected = "Timur"
        val actual = dataDefine.arahAngin("E")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 6`() = runTest {
        val expected = "Timur-Tenggara"
        val actual = dataDefine.arahAngin("ESE")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 7`() = runTest {
        val expected = "Tenggara"
        val actual = dataDefine.arahAngin("SE")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 8`() = runTest {
        val expected = "Tenggara-Selatan"
        val actual = dataDefine.arahAngin("SSE")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 9`() = runTest {
        val expected = "Selatan"
        val actual = dataDefine.arahAngin("S")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 10`() = runTest {
        val expected = "Selatan-Barat Daya"
        val actual = dataDefine.arahAngin("SSW")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 11`() = runTest {
        val expected = "Barat Daya"
        val actual = dataDefine.arahAngin("SW")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 12`() = runTest {
        val expected = "Barat-Barat Daya"
        val actual = dataDefine.arahAngin("WSW")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 13`() = runTest {
        val expected = "Barat"
        val actual = dataDefine.arahAngin("W")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 14`() = runTest {
        val expected = "Barat-Barat Laut"
        val actual = dataDefine.arahAngin("WNW")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 15`() = runTest {
        val expected = "Barat Laut"
        val actual = dataDefine.arahAngin("NW")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 16`() = runTest {
        val expected = "Utara-Barat Laut"
        val actual = dataDefine.arahAngin("NNW")
        assertEquals(expected, actual) }
    @Test fun `arahAngin jalur 17`() = runTest {
        val expected = "Kondisi cuaca tak terdefinisi"
        val actual = dataDefine.arahAngin("an")
        assertEquals(expected, actual) }
}