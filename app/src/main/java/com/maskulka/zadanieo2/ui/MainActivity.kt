package com.maskulka.zadanieo2.ui

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.maskulka.zadanieo2.R
import com.maskulka.zadanieo2.databinding.ActivityMainBinding
import com.maskulka.zadanieo2.utils.handleOnNavigateUp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        val couldNavigateUp = handleOnNavigateUp(listOf(R.id.navHostFragment))

        if (!couldNavigateUp) onBackPressed()

        return couldNavigateUp
    }
}
