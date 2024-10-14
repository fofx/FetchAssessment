package com.fofxlabs.fetchassessment.utils

import kotlin.random.Random

data class UniqueWrapper<T>(
    val value: T,
    val random: Long = Random.nextLong()
)
