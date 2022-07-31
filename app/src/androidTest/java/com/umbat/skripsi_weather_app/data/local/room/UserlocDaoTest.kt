package com.umbat.skripsi_weather_app.data.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.umbat.skripsi_weather_app.data.room.UserlocDatabase
import com.umbat.skripsi_weather_app.utils.DataDummy
import com.umbat.skripsi_weather_app.utils.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.Assert.*

class UserlocDaoTest {
    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: UserlocDatabase
    private lateinit var dao: UserlocDao
    private val sampleUserloc = DataDummy.generateDummyUserlocEntity()
    @Before fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UserlocDatabase::class.java
        ).build()
        dao = database.userlocDao() }
    @After fun closeDb() = database.close()

    @Test fun saveUserloc_Success() = runBlockingTest {
        dao.addDataLoc(sampleUserloc)
        Assert.assertTrue(dao.isExist()) }
    @Test fun deleteUserloc_Success() =  runBlockingTest {
        dao.addDataLoc(sampleUserloc)
        Assert.assertTrue(dao.isExist())
        dao.deleteDataLoc()
        Assert.assertFalse(dao.isExist()) }
    @Test fun readUserloc_Success() = runBlockingTest {
        dao.addDataLoc(sampleUserloc)
        val actualdata = dao.getLoc().getOrAwaitValue()
        Assert.assertEquals(sampleUserloc.prov, actualdata.prov) }
}