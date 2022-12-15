package com.ponkratov.autored.presentation.ui.home.tab.account.ridedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ponkratov.autored.databinding.FragmentRideDetailsLesseeBinding
import com.ponkratov.autored.databinding.FragmentRideDetailsLessorBinding

class RideDetailsLesseeFragment : Fragment() {

    private var _binding: FragmentRideDetailsLesseeBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRideDetailsLesseeBinding.inflate(inflater, container, false)
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