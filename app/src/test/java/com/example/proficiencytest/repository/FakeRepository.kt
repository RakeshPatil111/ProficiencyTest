package com.example.proficiencytest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proficiencytest.model.response.Fact
import com.example.proficiencytest.model.response.Row
import com.example.proficiencytest.util.Resource

class FakeRepository : FactRepository {

    private val factsList = mutableListOf<Row>()

    private val factsLiveData = MutableLiveData<List<Row>>(factsList)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value : Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun insertFacts(facts: List<Row>) {
        factsList.addAll(facts)
    }

    override fun getAllFacts(): LiveData<List<Row>> {
        return factsLiveData
    }

    override suspend fun getFactsFromServer(): Resource<Fact> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else Resource.Success(Fact(factsList, ""))
    }
}