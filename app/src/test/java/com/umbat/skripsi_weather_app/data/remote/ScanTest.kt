package com.umbat.skripsi_weather_app.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

    private lateinit var scan: Scan
    private lateinit var scanGet: MutableList<Weather>

    @Before
    fun setUp() {
        scan = Scan()
    }

    @Test
    fun `getContent of kecamatan Aceh Barat, Aceh`() = runTest {
        scanGet = scan.getContent("501409","aceh")
        assertTrue(scanGet.isNotEmpty())
    }
    @Test
    fun `getContent of kecamatan Boja, Jawa Tengah`() = runTest {
        scanGet = scan.getContent("5010069","jawatengah")
        assertTrue(scanGet.isNotEmpty())
    }
    @Test
    fun `getContent of kecamatan Adonara, NTT`() = runTest {
        scanGet = scan.getContent("5008970","ntt")
        assertTrue(scanGet.isNotEmpty())
    }

//    @Test
//    fun `when getContent error MutableList Should empty`() = runTest {
//        scanGet = scan.getContent("random","indonesia")
//        assertTrue(scanGet.isEmpty())
//    }
}