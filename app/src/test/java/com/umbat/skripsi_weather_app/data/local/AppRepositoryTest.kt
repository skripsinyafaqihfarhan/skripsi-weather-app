package com.umbat.skripsi_weather_app.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.umbat.skripsi_weather_app.data.FakeUserlocDao
import com.umbat.skripsi_weather_app.data.FakeWeatherDao
import com.umbat.skripsi_weather_app.data.local.room.UserlocDao
import com.umbat.skripsi_weather_app.data.room.WeatherDao
import com.umbat.skripsi_weather_app.utils.DataDummy
import com.umbat.skripsi_weather_app.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@ExperimentalCoroutinesApi
class AppRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var userlocdao: UserlocDao
    private lateinit var weatherdao: WeatherDao
    private lateinit var repo: AppRepository


    @Before
    fun setUp() {
        userlocdao = FakeUserlocDao()
        weatherdao = FakeWeatherDao()
        repo = AppRepository(weatherdao,userlocdao)
    }

    @Test
    fun `when checkDataLoc no data Should check status false` () = runTest {
        val checkData = userlocdao.isExist()
        assertFalse(checkData)
    }

    @Test
    fun `when addDataLoc data Should exist in Userloc and status is true`() = runTest {
        val sampleDataloc = DataDummy.generateDummyUserlocEntity()
        userlocdao.addDataLoc(sampleDataloc)
        val checkData = userlocdao.isExist()
        assertTrue(checkData)
    }

    @Test
    fun `when deleteDataloc data Should empty in Userloc and status is false`() = runTest {
        val sampleDataloc = DataDummy.generateDummyUserlocEntity()
        userlocdao.addDataLoc(sampleDataloc)
        userlocdao.deleteDataLoc()
        val checkData = userlocdao.isExist()
        assertFalse(checkData)
    }

    @Test
    fun `when getDataLoc Should equal with dummy`() = runTest {
        val sampleDataloc = DataDummy.generateDummyUserlocEntity()
        userlocdao.addDataLoc(sampleDataloc)
        val sampleData = sampleDataloc.prov
        val actualData = userlocdao.getLoc().value?.prov
        assertEquals(sampleData,actualData)
    }

    @Test
    fun `when addDataWeather Should not null`() = runTest {
        val sampleData = DataDummy.generateDummyWeatherEntity()
        weatherdao.addDataToLocal(sampleData)
        val dummyTime = sampleData.dateTime
        val actualData = weatherdao.readData(dummyTime)
        assertNotNull(actualData)
    }

    @Test
    fun `when deleteDataWeather Should null`() = runTest {
        val sampleData = DataDummy.generateDummyWeatherEntity()
        weatherdao.addDataToLocal(sampleData)
        val dummyTime = sampleData.dateTime
        weatherdao.deleteWeatherData()
        val actualData = weatherdao.readData(dummyTime).value?.windDr
        assertNull(actualData)
    }

    @Test
    fun `when readDataWeather Should equal with entity dummy`() = runTest {
        val addData = DataDummy.generateDummyWeatherEntity()
        weatherdao.addDataToLocal(addData)
        val dummyTime = addData.dateTime
        val readData = weatherdao.readData(dummyTime)
        val sampleData = addData.windDr
        val actualData = readData.value?.windDr
        assertEquals(sampleData,actualData)
    }
}