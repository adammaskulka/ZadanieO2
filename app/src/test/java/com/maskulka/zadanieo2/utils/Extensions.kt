package com.maskulka.zadanieo2.utils

import com.maskulka.zadanieo2.R


fun String.getStringResourceId() = try {
    R.string::class.java.getField(this).getInt(null)
} catch (e: Exception) {
    throw IllegalArgumentException("unknown string: $this")
}