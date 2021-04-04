package com.example.proficiencytest.repository

import androidx.lifecycle.LiveData
import com.example.proficiencytest.R
import com.example.proficiencytest.model.response.Fact
import com.example.proficiencytest.model.response.Row
import com.example.proficiencytest.repository.db.FactDAO
import com.example.proficiencytest.repository.db.FactDatabase
import com.example.proficiencytest.repository.network.FactAPI
import com.example.proficiencytest.util.Resource
import com.example.proficiencytest.util.ResourceHelper
import javax.inject.Inject

class DefaultRepository @Inject constructor(private val factDAO: FactDAO,
    private val factAPI: FactAPI) : FactRepository {

    override suspend fun insertFacts(facts: List<Row>): List<Long> {
        return factDAO.insertAllFacts(facts)
    }

    override fun getAllFacts(): LiveData<List<Row>> {
        return factDAO.getAllFacts()
    }

    override suspend fun getFactsFromServer(): Resource<Fact> {
        return try {
            val response = factAPI.getFacts()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error(ResourceHelper.getString(R.string.msg_server_error), null)
            } else {
                Resource.Error(ResourceHelper.getString(R.string.msg_server_error), null)
            }
        } catch (e : Exception) {
            Resource.Error(ResourceHelper.getString(R.string.msg_pls_check_internet), null)
        }
    }
}