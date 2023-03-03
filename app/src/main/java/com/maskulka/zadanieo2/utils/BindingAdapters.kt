package com.maskulka.zadanieo2.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

@BindingAdapter(value = ["toolbarTitle"])
fun setDisplayHomeAsUpEnabled(toolbar: Toolbar, title: String) {
    with(toolbar) {
        val activity = context.scanForActivity() as? AppCompatActivity
        activity?.let {
            it.setSupportActionBar(this)
            it.supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setDisplayShowTitleEnabled(true)
                actionBar.title = title
            }
        }
    }
}

fun Context.scanForActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.scanForActivity()
    else -> null
}