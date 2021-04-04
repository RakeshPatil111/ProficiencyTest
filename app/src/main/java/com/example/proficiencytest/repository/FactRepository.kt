package com.example.proficiencytest.repository

import androidx.lifecycle.LiveData
import com.example.proficiencytest.model.response.Fact
import com.example.proficiencytest.model.response.Row
import com.example.proficiencytest.util.Resource

interface FactRepository {
    suspend fun insertFacts(facts: List<Row>) : List<Long>

    fun getAllFacts(): LiveData<List<Row>>

    suspend fun getFactsFromServer() : Resource<Fact>
}