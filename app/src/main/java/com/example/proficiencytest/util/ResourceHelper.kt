package com.example.proficiencytest.util

import com.example.proficiencytest.FactApplication

object ResourceHelper {
    fun getString(id : Int) : String{
        return FactApplication.context.resources.getString(id)
    }
}