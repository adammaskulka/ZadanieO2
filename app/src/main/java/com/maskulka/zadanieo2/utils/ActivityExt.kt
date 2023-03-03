package com.maskulka.zadanieo2.utils

import android.app.Activity
import android.view.View
import androidx.navigation.findNavController

fun Activity.handleOnNavigateUp(containers: List<Int>) = containers.any {
    findViewById<View>(it) != null && findNavController(it).navigateUp()
}