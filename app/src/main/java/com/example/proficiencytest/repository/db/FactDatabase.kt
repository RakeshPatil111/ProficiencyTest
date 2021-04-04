package com.example.proficiencytest.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proficiencytest.model.response.Row

/**
 * DB class
 * Instantiated through hilt
 * Add db name, column name in constant file
 * */
@Database(
    entities = [Row::class],
    version = 1,
    exportSchema = false
)
abstract class FactDatabase : RoomDatabase() {
    abstract fun getFactDao(): FactDAO
}