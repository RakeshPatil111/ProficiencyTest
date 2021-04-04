package com.example.proficiencytest.repository.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.proficiencytest.HiltTestRunner
import com.example.proficiencytest.getOrAwaitValue
import com.example.proficiencytest.model.response.Row
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class FactDAOTest {
    @get:Rule
    var hiltRunner = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var factDatabase: FactDatabase
    private lateinit var factDAO: FactDAO

    @Before
    fun setup() {
        hiltRunner.inject()
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
        var fact1 = Row(description = "sample description", imageHref = null, title = "Sample1")
        var fact2 = Row(description = "sample description", imageHref = null, title = "Sample2")
        var fact3 = Row(description = "sample description", imageHref = null, title = "Sample3")
        factDAO.insertAllFacts(listOf(fact1, fact2, fact3))

        val facts = factDAO.getAllFacts().getOrAwaitValue()

        assertThat(facts).hasSize(3)
    }
}