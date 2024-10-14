package com.fofxlabs.fetchassessment.data.externalModel

data class Item(
    val id: Int,
    val listId: Int,
    val name: String?,
)

fun List<Item>.sortByListIdAndId(): List<Item> {
    return filter { it.name != null && it.name.isNotBlank() }
        .sortedWith(compareBy<Item> { it.listId }.thenBy { it.id })
}