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
import java.io.IOException
import java.lang.IllegalArgumentException
import java.util.*

class DataDefineTest1 {
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule val mainDispatcherRule = MainDispatcherRule()
    private lateinit var dataDefine: DataDefine
    @Before fun setUp() { dataDefine = DataDefine() }

    @Test fun `kondisiCuaca jalur 1`() = runTest {
        val expected = "Cerah"
        val actual = dataDefine.kondisiCuaca("0")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 2`() = runTest {
        val expected = "Cerah Berawan"
        val actual = dataDefine.kondisiCuaca("1")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 3`() = runTest {
        val expected = "Cerah Berawan"
        val actual = dataDefine.kondisiCuaca("2")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 4`() = runTest {
        val expected = "Berawan"
        val actual = dataDefine.kondisiCuaca("3")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 5`() = runTest {
        val expected = "Berawan Tebal"
        val actual = dataDefine.kondisiCuaca("4")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 6`() = runTest {
        val expected = "Udara Kabur"
        val actual = dataDefine.kondisiCuaca("5")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 7`() = runTest {
        val expected = "Asap"
        val actual = dataDefine.kondisiCuaca("10")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 8`() = runTest {
        val expected = "Kabut"
        val actual = dataDefine.kondisiCuaca("45")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 9`() = runTest {
        val expected = "Hujan Ringan"
        val actual = dataDefine.kondisiCuaca("60")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 10`() = runTest {
        val expected = "Hujan Sedang"
        val actual = dataDefine.kondisiCuaca("61")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 11`() = runTest {
        val expected = "Hujan Lebat"
        val actual = dataDefine.kondisiCuaca("63")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 12`() = runTest {
        val expected = "Hujan Lokal"
        val actual = dataDefine.kondisiCuaca("80")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 13`() = runTest {
        val expected = "Hujan Petir"
        val actual = dataDefine.kondisiCuaca("95")
        assertEquals(expected, actual) }
    @Test fun `kondisiCuaca jalur 14`() = runTest {
        val expected = "Hujan Petir"
        val actual = dataDefine.kondisiCuaca("97")
        assertEquals(expected, actual) }
//    @Test fun `kondisiCuaca jalur 15`() = runTest {
//        val e: IllegalArgumentException
//        val expected = e
//        val actual = dataDefine.kondisiCuaca("as")
//        assertEquals(expected, actual) }
}