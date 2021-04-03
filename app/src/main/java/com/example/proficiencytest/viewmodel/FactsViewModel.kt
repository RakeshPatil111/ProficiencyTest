package com.example.proficiencytest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proficiencytest.model.response.Fact
import com.example.proficiencytest.model.response.Row
import com.example.proficiencytest.repository.FactRepository
import com.example.proficiencytest.util.NetworkAvailability
import com.example.proficiencytest.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class FactsViewModel(
    private val application: Application,
    private val repository: FactRepository
) : ViewModel() {

    var factsResponse : Fact? = null
    val factResponseLiveData : MutableLiveData<Resource<Fact>> = MutableLiveData()
    var factsLocalLiveData : LiveData<List<Row>>

    init {
        factsLocalLiveData = repository.getAllUsers()
    }

   /**
    * Get Data
    *  1. If network is available, get it from local storage
    *  2. If network available, get data from server
    * */
    fun getFacts() = viewModelScope.launch {
        if (NetworkAvailability.hasInternetConnection(application)) {
            getFactsFromServer()
        } else {
            getFactsFromLocalStorage()
        }
    }

    private fun getFactsFromLocalStorage() {
        factsLocalLiveData = repository.getAllUsers()

    }
    private suspend fun getFactsFromServer() {
        factResponseLiveData.postValue(Resource.Loading())
        try {
            if (NetworkAvailability.hasInternetConnection(application)) {
                val response = repository.getFactsFromServer()
                factResponseLiveData.postValue(handleResponse(response))
            }
            else {
                // If we want to show error message for connection
                factResponseLiveData.postValue(Resource.Error("No Internet Connection"))
                // Or lets give local data
                getFactsFromLocalStorage()
            }
        }
        catch (t : Throwable) {
            when(t) {
                // Unknown error for type conversion may occur
                // if so check with model and response
                is IOException -> factResponseLiveData.postValue(Resource.Error("Network Failure"))
                else -> factResponseLiveData.postValue(Resource.Error("Unknown error"))
            }
        }
    }

    /*
    Method body can be further changed
    if we want to add pagination or other things
     */
    private fun handleResponse(response: Response<Fact>): Resource<Fact>? {
        if (response.isSuccessful) {
            response.body()?.let {
                factsResponse = it
                // Lets save data to db
                insertAllFacts()
                return Resource.Success(it!!)
            }
        }
        return Resource.Error(response.message())
    }

    // Insert all facts in db
    fun insertAllFacts() = viewModelScope.launch {
        try {
            factsLocalLiveData.value?.let {
                val newItems = factsResponse?.rows?.subtract(it)
                newItems?.let { setOfFacts ->
                    repository.insertFacts(setOfFacts.toList())
                }
            }
        } catch (e : Exception) {
            Log.i("Error :: ", e.localizedMessage)
        }
    }
}