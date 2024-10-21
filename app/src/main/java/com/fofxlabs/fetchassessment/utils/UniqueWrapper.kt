package com.fofxlabs.fetchassessment.utils

import kotlin.random.Random

// Unique Wrapper for snackbar messages
sealed class UniqueWrapper {
    data class IntWrapper(val value: Int, val random: Long = Random.nextLong()) : UniqueWrapper()
    data class StringWrapper(val value: String, val random: Long = Random.nextLong()) : UniqueWrapper()
}