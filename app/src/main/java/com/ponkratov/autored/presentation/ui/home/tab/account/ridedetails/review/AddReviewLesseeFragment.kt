package com.ponkratov.autored.presentation.ui.home.tab.account.ridedetails.review

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.ponkratov.autored.databinding.FragmentAddReviewLesseeBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddReviewLesseeFragment : Fragment() {

    private var _binding: FragmentAddReviewLesseeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<AddReviewLesseeViewModel>()

    private val args by navArgs<AddReviewLesseeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAddReviewLesseeBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            progressCircular.isVisible = false
            layoutReview.isVisible = true
            checkbox5.isChecked = true

            buttonBack.setOnClickListener {
                findNavController().navigateUp()
            }

            buttonSend.setOnClickListener {
                val mark = when (radioGroup.checkedRadioButtonId) {
                    checkbox1.id -> 1
                    checkbox2.id -> 2
                    checkbox3.id -> 3
                    checkbox4.id -> 4
                    checkbox5.id -> 5
                    else -> 1
                }

                viewModel.onSendButtonClicked(
                    markUser = mark,
                    commentUser = editTextReview.text.toString(),
                    userTo = args.lessorId
                )
            }

            viewModel
                .loadingFlow
                .onEach {
                    progressCircular.isVisible = true
                    layoutReview.isVisible = false
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .errorFlow
                .onEach {
                    progressCircular.isVisible = false
                    layoutReview.isVisible = true
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
                    layoutReview.isVisible = true
                    editTextReview.text?.clear()
                    AlertDialog
                        .Builder(requireContext())
                        .setTitle("Отзыв")
                        .setMessage(it)
                        .setPositiveButton(android.R.string.ok, null)
                        .setOnDismissListener {
                            findNavController().navigateUp()
                        }
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