package com.fofxlabs.fetchassessment.data.network.model

import com.google.gson.annotations.SerializedName

data class NetworkItem(
    val id: Int,
    val listId: Int,
    val name: String?
)

// Extension function to extract the integer value from the name string
// Using this to sort list by name
// If sorting by the string value, then names will be listed out of order.
// For example, "Item 280" will be listed before "Item 29".
fun NetworkItem.getNameInt(): Int? {
    return name?.filter { it.isDigit() }?.toIntOrNull()
}