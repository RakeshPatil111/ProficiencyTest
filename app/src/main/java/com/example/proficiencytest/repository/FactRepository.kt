package com.example.proficiencytest.repository

import com.example.proficiencytest.model.response.Row
import com.example.proficiencytest.repository.db.FactDatabase
import com.example.proficiencytest.repository.network.RetrofitClient

class FactRepository(private val factDb : FactDatabase){
    suspend fun insertFacts(facts : List<Row>) = factDb.getFactDao().insertAllFacts(facts)

    fun getAllUsers() = factDb.getFactDao().getAllFacts()

    suspend fun getFactsFromServer() = RetrofitClient.api.getFacts()
}