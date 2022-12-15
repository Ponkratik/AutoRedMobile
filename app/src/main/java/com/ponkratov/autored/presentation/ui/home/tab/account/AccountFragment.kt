package com.ponkratov.autored.presentation.ui.home.tab.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ponkratov.autored.databinding.FragmentAccountBinding
import com.ponkratov.autored.presentation.extensions.addVerticalSpace
import com.ponkratov.autored.presentation.ui.home.tab.search.list.AdvertisementAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<AccountViewModel>()

    private val adapter by lazy {
        AdvertisementAdapter(
            context = requireContext(),
            onAdvertisementClicked = {
                findNavController().navigate(
                    AccountFragmentDirections.actionAccountToRideListLessee(Gson().toJson(it))
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAccountBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            progressCircular.isVisible = false

            buttonSettings.setOnClickListener {
                findNavController().navigate(AccountFragmentDirections.actionAccountToSettings())
            }

            buttonAddAdvertisement.setOnClickListener {
                findNavController().navigate(AccountFragmentDirections.actionAccountToAdvertisementAdd())
            }

            advertisementRecyclerView.adapter = adapter
            advertisementRecyclerView.addVerticalSpace()

            viewModel
                .loadingFlow
                .onEach {
                    progressCircular.isVisible = true
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .errorFlow
                .onEach {
                    progressCircular.isVisible = false
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
                    progressCircular.isVisible = false
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