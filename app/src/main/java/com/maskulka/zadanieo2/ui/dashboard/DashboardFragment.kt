package com.maskulka.zadanieo2.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.bind
import androidx.navigation.fragment.findNavController
import com.maskulka.zadanieo2.R
import com.maskulka.zadanieo2.databinding.FragmentDashboardBinding
import com.maskulka.zadanieo2.utils.collectIn
import com.maskulka.zadanieo2.utils.safeNavigateTo
import com.maskulka.zadanieo2.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val fragmentViewModel: DashboardViewModel by viewModel()

    private val viewBinding: FragmentDashboardBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = fragmentViewModel
        }

        fragmentViewModel.navDirections.collectIn(viewLifecycleOwner) {directions ->
            safeNavigateTo(directions)
        }
    }
}