package com.maskulka.zadanieo2.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import timber.log.Timber

fun Fragment.safeNavigateTo(directions: NavDirections) {
    try {
        findNavController().navigate(directions)
    } catch (e: IllegalArgumentException) {
        Timber.e(e.toString())
    }
}