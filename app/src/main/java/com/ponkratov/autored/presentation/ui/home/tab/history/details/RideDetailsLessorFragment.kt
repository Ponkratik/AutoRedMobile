package com.ponkratov.autored.presentation.ui.home.tab.history.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.ponkratov.autored.databinding.FragmentHistoryBinding
import com.ponkratov.autored.databinding.FragmentRideDetailsLessorBinding
import com.ponkratov.autored.presentation.ui.home.tab.history.HistoryViewModel
import com.ponkratov.autored.presentation.ui.home.tab.history.RideAdapter
import com.ponkratov.autored.presentation.ui.home.tab.search.list.SearchFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class RideDetailsLessorFragment : Fragment() {

    private var _binding: FragmentRideDetailsLessorBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRideDetailsLessorBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}