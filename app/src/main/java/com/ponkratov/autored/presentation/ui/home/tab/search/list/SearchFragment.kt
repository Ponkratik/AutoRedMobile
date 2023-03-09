package com.ponkratov.autored.presentation.ui.home.tab.search.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ponkratov.autored.databinding.FragmentSearchBinding
import com.ponkratov.autored.presentation.extensions.addVerticalSpace
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<SearchViewModel>()

    private val adapter by lazy {
        AdvertisementAdapter(
            context = requireContext(),
            onAdvertisementClicked = {
                findNavController().navigate(
                    SearchFragmentDirections.actionFragmentSearchToAdvertisementDetailsFragment(
                        Gson().toJson(it)
                    )
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSearchBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            layoutSwiperefresh.isRefreshing = true

            advertisementRecyclerView.adapter = adapter
            advertisementRecyclerView.addVerticalSpace()

            layoutSwiperefresh.setOnRefreshListener {
                viewModel.onRefreshSwiped()
            }

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
                    adapter.submitList(it.filter { ad -> ad.advertisement.verified })
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