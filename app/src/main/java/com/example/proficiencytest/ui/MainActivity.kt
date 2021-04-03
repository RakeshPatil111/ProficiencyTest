package com.example.proficiencytest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.proficiencytest.R
import com.example.proficiencytest.repository.FactRepository
import com.example.proficiencytest.repository.db.FactDatabase
import com.example.proficiencytest.viewmodel.FactsViewModel
import com.example.proficiencytest.viewmodel.provider.FactsViewModelProvider

class MainActivity : AppCompatActivity() {
    // TODO Add DI later
    lateinit var factsViewModel: FactsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factsDb = FactDatabase(this)
        val repository = FactRepository(factsDb)
        val provider = FactsViewModelProvider(application = application, repository)
        factsViewModel = ViewModelProvider(this, provider).get(FactsViewModel::class.java)
    }
}