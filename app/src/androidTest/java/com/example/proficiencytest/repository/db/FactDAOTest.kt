package com.example.proficiencytest.repository.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.proficiencytest.getOrAwaitValue
import com.example.proficiencytest.model.response.Row
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FactDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var factDatabase: FactDatabase
    private lateinit var factDAO: FactDAO

    @Before
    fun setup() {
        factDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FactDatabase::class.java
        ).allowMainThreadQueries().build()

        factDAO = factDatabase.getFactDao()
    }

    @After
    fun teardown() {
        factDatabase.close()
    }

    @Test
    fun testInsertFact() = runBlocking {
        var fact = Row(description = "sample description", imageHref = null, title = "Sample")
        factDAO.insertAllFacts(listOf(fact))

        val facts = factDAO.getAllFacts().getOrAwaitValue()

        assertThat(facts).contains(fact)
    }

    @Test
    fun testGetAllFacts() = runBlocking {
        var fact1 = Row(description = "sample description", imageHref = null, title = "Sample")
        var fact2 = Row(description = "sample description", imageHref = null, title = "Sample")
        var fact3 = Row(description = "sample description", imageHref = null, title = "Sample")
        factDAO.insertAllFacts(listOf(fact1, fact2, fact3))

        val facts = factDAO.getAllFacts().getOrAwaitValue()

        assertThat(facts).hasSize(3)
    }
}