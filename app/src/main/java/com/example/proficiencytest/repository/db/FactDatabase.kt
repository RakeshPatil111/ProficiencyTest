package com.example.proficiencytest.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proficiencytest.model.response.Fact
import com.example.proficiencytest.model.response.Row

@Database(
    entities = [Row::class],
    version = 1,
    exportSchema = false
)
abstract class FactDatabase : RoomDatabase() {
    abstract fun getFactDao(): FactDAO
}