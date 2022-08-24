package com.umbat.skripsi_weather_app.data.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.data.room.WeatherDao
import com.umbat.skripsi_weather_app.utils.DataDummy
import com.umbat.skripsi_weather_app.utils.getOrAwaitValue
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.Assert.*

class WeatherDaoTest {
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: WeatherDatabase
    private lateinit var dao: WeatherDao
    private val sampleWeather = DataDummy.generateDummyWeatherEntity()
    @Before fun initDb() { database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDatabase::class.java
        ).build()
        dao = database.weatherDao() }
    @After fun closeDb() = database.close()

    @Test fun saveWeather_Success() = runBlockingTest {
        dao.addDataToLocal(sampleWeather)
        assertTrue(dao.isExist()) }
    @Test fun deleteWeather_Success() =  runBlockingTest {
        dao.addDataToLocal(sampleWeather)
        assertTrue(dao.isExist())
        dao.deleteWeatherData()
        assertFalse(dao.isExist()) }
    @Test fun readWeather_Success() = runBlockingTest {
        dao.addDataToLocal(sampleWeather)
        val dummyTime = sampleWeather.dateTime
        val actualData = dao.readData(dummyTime).getOrAwaitValue()
        Assert.assertEquals(sampleWeather.windDr,actualData.windDr) }
}