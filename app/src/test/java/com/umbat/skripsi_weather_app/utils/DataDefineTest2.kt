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
}