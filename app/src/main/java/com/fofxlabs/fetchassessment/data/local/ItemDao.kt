package com.fofxlabs.fetchassessment.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fofxlabs.fetchassessment.data.local.model.LocalItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {


    /**
     * Observe list of LocalItems
     *
     * @return a Flow of all LocalItems.
     */
    @Query("SELECT * FROM item")
    fun observeAllItems(): Flow<List<LocalItem>>

    /**
     * Select all LocalItems from the item table.
     *
     * @return all LocalItems.
     */
    @Query("SELECT * FROM item")
    suspend fun getAll(): List<LocalItem>


    @Upsert
    fun upsertItem(item: LocalItem)

    /**
     * Insert or update items in  in the database.
     *
     * @param items: items to be inserted or updated.
     */
    @Upsert
    suspend fun upsertAll(items: List<LocalItem>)

    /**
     * Delete all LocalItems.
     */
    @Query("DELETE FROM item")
    suspend fun deleteAll()
}
