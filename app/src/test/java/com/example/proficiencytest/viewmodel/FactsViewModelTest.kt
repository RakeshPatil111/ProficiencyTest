package com.example.proficiencytest.viewmodel

import com.example.proficiencytest.repository.FakeRepository
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class FactsViewModelTest {

    private lateinit var viewModel: FactsViewModel

    @Before
    fun setUp() {
        viewModel = FactsViewModel(FakeRepository())
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testGetDataFromServer() {
        
    }
}