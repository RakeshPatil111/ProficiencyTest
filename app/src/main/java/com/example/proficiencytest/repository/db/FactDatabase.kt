package com.example.proficiencytest.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proficiencytest.model.response.Fact
import com.example.proficiencytest.model.response.Row

@Database(
    entities = [Fact::class],
    version = 1,
    exportSchema = false
)
abstract class FactDatabase : RoomDatabase() {
    abstract fun getFactDao(): FactDAO

    companion object {
        @Volatile
        private var factDbInstance: FactDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = factDbInstance ?: synchronized(LOCK) {
            factDbInstance ?: createDatabaseInstance(context).also {
                factDbInstance = it
            }
        }

        private fun createDatabaseInstance(context: Context) =
            Room.databaseBuilder(
                context,
                FactDatabase::class.java,
                "facts_db.db"
            ).fallbackToDestructiveMigration().build()
    }
}