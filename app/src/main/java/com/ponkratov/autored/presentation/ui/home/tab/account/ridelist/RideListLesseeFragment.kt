package com.ponkratov.autored.presentation.ui.home.tab.account.ridelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ponkratov.autored.databinding.FragmentHistoryBinding
import com.ponkratov.autored.databinding.FragmentRideListLesseeBinding
import com.ponkratov.autored.domain.model.response.AdvertisementResponse
import com.ponkratov.autored.presentation.extensions.addVerticalSpace
import com.ponkratov.autored.presentation.ui.home.tab.account.AccountFragmentDirections
import com.ponkratov.autored.presentation.ui.home.tab.history.HistoryFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class RideListLesseeFragment : Fragment() {
    private var _binding: FragmentRideListLesseeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<RideListLesseeViewModel>()

    private val args by navArgs<RideListLesseeFragmentArgs>()

    private val adapter by lazy {
        RideLesseeAdapter(
            context = requireContext(),
            onRideClicked = {
                findNavController().navigate(
                    RideListLesseeFragmentDirections.actionRideListToRideDetailsLessee(Gson().toJson(it))
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRideListLesseeBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            layoutSwiperefresh.setOnRefreshListener {
                viewModel.onRefreshSwiped(Gson().fromJson(args.advertisementResponse, AdvertisementResponse::class.java).advertisement.id)
            }

            //layoutSwiperefresh.isRefreshing = true

            advertisementRecyclerView.adapter = adapter
            advertisementRecyclerView.addVerticalSpace()

            viewModel
                .loadingFlow
                .onEach {
                    layoutSwiperefresh.isRefreshing = true
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .errorFlow
                .onEach {
                    Snackbar.make(
                        requireView(),
                        it.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .dataFlow
                .onEach {
                    adapter.submitList(it)
                    layoutSwiperefresh.isRefreshing = false
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .getDataFlow
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}