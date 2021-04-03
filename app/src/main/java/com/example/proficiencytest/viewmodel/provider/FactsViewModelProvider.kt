package com.example.proficiencytest.viewmodel.provider

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proficiencytest.repository.FactRepository
import com.example.proficiencytest.viewmodel.FactsViewModel

/**
 * View model provider for view model
 * requires two params application and repo
 * return variable of type view model
 */
class FactsViewModelProvider (
    private val application: Application,
    private val repository: FactRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FactsViewModel(application = application, repository = repository) as T
    }
}