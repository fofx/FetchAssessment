package com.fofxlabs.fetchassessment.data

import com.fofxlabs.fetchassessment.data.externalModel.Item
import com.fofxlabs.fetchassessment.data.local.model.LocalItem
import com.fofxlabs.fetchassessment.data.network.model.NetworkItem

/**
 * This assessment uses a very basic model, but this is the type of data mapping I would use
 * if we needed to handle more complexity for saving the data locally for offline use.
  */

fun LocalItem.toExternal() = Item(
    id = id,
    listId = listId,
    name = name,
)

fun NetworkItem.toLocal() = LocalItem(
    id = id,
    listId = listId,
    name = name,
)

fun Item.toLocal() = LocalItem(
    id = id,
    listId = listId,
    name = name,
)

fun List<LocalItem>.toExternal() = map(LocalItem::toExternal)
fun List<NetworkItem>.toLocal() = map(NetworkItem::toLocal)
