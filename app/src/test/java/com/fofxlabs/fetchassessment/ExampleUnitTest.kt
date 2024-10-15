package com.fofxlabs.fetchassessment

import com.fofxlabs.fetchassessment.data.externalModel.Item
import com.fofxlabs.fetchassessment.data.externalModel.sortByListIdAndId
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun sortItemList() {
        val items = listOf(
            Item(id = 1, listId = 1, name = "Item 1"),
            Item(id = 2, listId = 2, name = "Item 2"),
            Item(id = 3, listId = 1, name = "Item 3"),
            Item(id = 4, listId = 2, name = null), // Should be filtered out
            Item(id = 5, listId = 1, name = ""), // Should be filtered out
            Item(id = 6, listId = 3, name = "Item 6"),
            Item(id = 7, listId = 2, name = "Item 7"),
            Item(id = 8, listId = 1, name = "Item 8"),
        )

        val sortedItems = items.sortByListIdAndId()
        assertEquals(sortedItems.size, 6)

        assertEquals(sortedItems[0].id, 1)
        assertEquals(sortedItems[1].id, 3)
        assertEquals(sortedItems[2].id, 8)
        assertEquals(sortedItems[3].id, 2)
        assertEquals(sortedItems[4].id, 7)
        assertEquals(sortedItems[5].id, 6)
    }
}