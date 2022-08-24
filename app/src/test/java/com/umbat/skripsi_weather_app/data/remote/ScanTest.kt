package com.umbat.skripsi_weather_app.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.umbat.skripsi_weather_app.data.FakeUserlocDao
import com.umbat.skripsi_weather_app.data.FakeWeatherDao
import com.umbat.skripsi_weather_app.data.local.AppRepository
import com.umbat.skripsi_weather_app.data.local.entity.Weather
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao
import com.umbat.skripsi_weather_app.data.room.WeatherDao
import com.umbat.skripsi_weather_app.utils.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ScanTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var kodeKec: String
    private lateinit var provID: String
    private lateinit var scan: Scan
    private lateinit var scanGet: MutableList<Weather>

    @Before
    fun setUp() {
        kodeKec = "5008825"
        provID = "banten"
        scan = Scan()
    }

    @Test
    fun `when getContent Should add MutableList of Weather`() = runTest {
        scanGet = scan.getContent(kodeKec,provID)
        assertTrue(scanGet.isNotEmpty())
    }

//    @Test
//    fun `when getContent error MutableList Should empty`() = runTest {
//        scanGet = scan.getContent("random","indonesia")
//        assertTrue(scanGet.isEmpty())
//    }
}