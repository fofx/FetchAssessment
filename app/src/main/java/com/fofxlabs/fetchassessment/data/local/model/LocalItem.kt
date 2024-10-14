package com.fofxlabs.fetchassessment.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "item",
)
data class LocalItem(
    @PrimaryKey val id: Int,
    val listId: Int,
    val name: String?,
)