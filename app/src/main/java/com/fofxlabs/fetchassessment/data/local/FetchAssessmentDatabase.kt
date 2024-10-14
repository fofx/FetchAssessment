package com.fofxlabs.fetchassessment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fofxlabs.fetchassessment.data.local.model.LocalItem

@Database(entities = [LocalItem::class], version = 1, exportSchema = false)
abstract class FetchAssessmentDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}