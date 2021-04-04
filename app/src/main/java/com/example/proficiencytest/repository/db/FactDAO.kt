package com.example.proficiencytest.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.proficiencytest.model.response.Row

/**
 * Base Repository
 * Implement this repo in classes
 * Add all db operations here, only method declarations
 * */
@Dao
interface FactDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFacts(facts : List<Row>) : List<Long>

    @Query("select * from facts")
    fun getAllFacts() : LiveData<List<Row>>
}