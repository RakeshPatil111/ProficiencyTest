package com.example.proficiencytest.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proficiencytest.model.response.Fact
import com.example.proficiencytest.model.response.Row
import com.example.proficiencytest.repository.FactRepository
import com.example.proficiencytest.util.Event
import com.example.proficiencytest.util.Resource
import kotlinx.coroutines.launch

class FactsViewModel @ViewModelInject constructor(
    private val repository: FactRepository
) : ViewModel() {

    private val _factResponseLiveData: MutableLiveData<Event<Resource<Fact>>> = MutableLiveData()
    val factResponseLiveData = _factResponseLiveData
    var factsLocalLiveData: LiveData<List<Row>> = repository.getAllFacts()

    // TODO add check for internet, if internet is not available, return local data
    fun getFacts() = viewModelScope.launch {
        getFactsFromServer()
    }

    // Get Data from server
    private suspend fun getFactsFromServer() {
        _factResponseLiveData.value = Event(Resource.Loading())
        val response = repository.getFactsFromServer()
        _factResponseLiveData.value = Event(response)
    }

    // Insert all facts in db
    fun insertAllFacts(facts: List<Row>) = viewModelScope.launch {
        try {
            var result = repository.insertFacts(facts)
        } catch (e: Exception) {
            Log.e("Error :: ", e.localizedMessage)
        }
    }
}