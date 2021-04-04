package com.example.proficiencytest.util

import com.example.proficiencytest.FactApplication

/**
 * Helper to provide all the resources(strings, colors, drawables etc) in classes
 * */
object ResourceHelper {
    fun getString(id : Int) : String{
        return FactApplication.context.resources.getString(id)
    }
}