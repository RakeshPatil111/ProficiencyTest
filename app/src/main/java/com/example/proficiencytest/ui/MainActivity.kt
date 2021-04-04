package com.example.proficiencytest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proficiencytest.R
import dagger.hilt.android.AndroidEntryPoint

// TODO current architecture for app is MVVM, try to convert it to MVVM + Clean

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}