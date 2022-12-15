package com.ponkratov.autored.presentation.ui.home.tab.account.support

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ponkratov.autored.databinding.FragmentSupportRequestBinding
import com.ponkratov.autored.presentation.ui.login.LoginFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupportRequestFragment : Fragment() {

    private var _binding: FragmentSupportRequestBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<SupportRequestViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSupportRequestBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            progressCircular.isVisible = false
            layoutRequest.isVisible = true

            buttonBack.setOnClickListener {
                findNavController().navigateUp()
            }

            buttonSend.setOnClickListener {
                viewModel.onSendButtonClicked(editTextSupportRequest.text.toString())
            }

            viewModel
                .loadingFlow
                .onEach {
                    progressCircular.isVisible = true
                    layoutRequest.isVisible = false
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .errorFlow
                .onEach {
                    progressCircular.isVisible = false
                    layoutRequest.isVisible = true
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
                    progressCircular.isVisible = false
                    layoutRequest.isVisible = true
                    editTextSupportRequest.text?.clear()
                    AlertDialog
                        .Builder(requireContext())
                        .setTitle("Сообщение в службу тех.поддержки")
                        .setMessage(it)
                        .setPositiveButton(android.R.string.ok, null)
                        .show()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .getResponseFlow
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}