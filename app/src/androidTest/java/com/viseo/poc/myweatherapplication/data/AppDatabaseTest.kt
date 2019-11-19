package com.viseo.poc.myweatherapplication.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var cityDao: CityDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        // Create DB
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        cityDao = db.cityDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCity() = runBlocking {
        val city = City("city1", 0.1, 0.1, false)
        cityDao.insert(city)
        val allWords = cityDao.getAll()
        Assert.assertEquals(city.name, allWords[0].name)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() = runBlocking {
        val city = City("city1", 0.1, 0.1, true)
        cityDao.insert(city)
        val city2 = City("city2", 0.1, 0.1, true)
        cityDao.insert(city2)
        cityDao.deleteAll()
        val allWords = cityDao.getAll()
        Assert.assertTrue(allWords.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCityHistory() = runBlocking {
        val city0 = City("city0", 0.1, 0.1, true)
        val city1 = City("city1", 0.2, 0.1, true)
        val city2 = City("city2", 0.3, 0.1, true)
        cityDao.insert(city0)
        cityDao.insert(city1)
        cityDao.insert(city2)
        val allWords = cityDao.getCityHistory().waitForValue()

        //Expected city order: The last inserted city at top (city2 - city1 - city0)
        Assert.assertEquals(city2.name, allWords[0].name)
        Assert.assertEquals(city1.name, allWords[1].name)
        Assert.assertEquals(city0.name, allWords[2].name)
    }


    /**
     * Get the value from a LiveData object. We're waiting for LiveData to emit, for 2 seconds.
     * Once we got a notification via onChanged, we stop observing.
     */
    @Throws(InterruptedException::class)
    private fun <T> LiveData<T>.waitForValue(): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data[0] = o
                latch.countDown()
                this@waitForValue.removeObserver(this)
            }
        }
        this.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }
}