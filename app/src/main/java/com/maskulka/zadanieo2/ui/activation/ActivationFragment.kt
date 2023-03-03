package com.maskulka.zadanieo2.ui.activation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maskulka.zadanieo2.R
import com.maskulka.zadanieo2.databinding.FragmentActivationBinding
import com.maskulka.zadanieo2.databinding.FragmentDashboardBinding
import com.maskulka.zadanieo2.ui.scratch.ScratchViewModel
import com.maskulka.zadanieo2.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActivationFragment : Fragment(R.layout.fragment_activation) {

    private val fragmentViewModel: ActivationViewModel by viewModel()

    private val viewBinding: FragmentActivationBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = fragmentViewModel
        }
    }
}