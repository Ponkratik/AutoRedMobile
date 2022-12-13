package com.ponkratov.autored.presentation.ui.home.tab.search.details

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
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ponkratov.autored.R
import com.ponkratov.autored.databinding.FragmentAdvertisementDetailsBinding
import com.ponkratov.autored.domain.model.response.AdvertisementResponse
import com.ponkratov.autored.domain.model.toListOptions
import com.ponkratov.autored.presentation.extensions.addHorisontalSpace
import com.ponkratov.autored.presentation.ui.login.LoginFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AdvertisementDetailsFragment : Fragment() {

    private var _binding: FragmentAdvertisementDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<AdvertisementDetailsViewModel>()

    private val args by navArgs<AdvertisementDetailsFragmentArgs>()

    private val imageAdapter by lazy {
        ImageAdapter(
            context = requireContext()
        )
    }

    private val featureAdapter by lazy {
        FeatureAdapter(
            context = requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentAdvertisementDetailsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            progressCircular.isVisible = false
            layoutAdvertisement.isVisible = true

            photoRecyclerView.adapter = imageAdapter
            photoRecyclerView.addHorisontalSpace()

            val advertisementResponse =
                Gson().fromJson(args.advertisementResponse, AdvertisementResponse::class.java)

            imageAdapter.submitList(advertisementResponse.photoPaths.sortedDescending())

            carName.text = getString(
                R.string.text_car_make_model_year,
                advertisementResponse.car.make,
                advertisementResponse.car.model,
                SimpleDateFormat("yyyy", Locale.US).format(advertisementResponse.car.manufacturedYear)
            )

            textPricePerDay.text =
                getString(R.string.text_price, advertisementResponse.advertisement.pricePerDay)
            textPricePerWeek.text =
                getString(R.string.text_price, advertisementResponse.advertisement.pricePerWeek)
            textPricePerMonth.text =
                getString(R.string.text_price, advertisementResponse.advertisement.pricePerMonth)

            textCarType.text = advertisementResponse.car.carType
            textFuelType.text = advertisementResponse.car.fuelType
            textTransmissionType.text = advertisementResponse.car.transmissionType
            textDoors.text = advertisementResponse.car.doors.toString()
            textSeats.text = advertisementResponse.car.seats.toString()
            textColor.text = advertisementResponse.car.color

            val layoutManager = FlexboxLayoutManager(requireContext())
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.justifyContent = JustifyContent.SPACE_EVENLY
            carfeatureRecyclerView.layoutManager = layoutManager
            carfeatureRecyclerView.adapter = featureAdapter
            featureAdapter.submitList(advertisementResponse.carFeatureList.toListOptions())

            buttonBack.setOnClickListener {
                findNavController().navigateUp()
            }

            buttonBook.setOnClickListener {
                viewModel.onBookButtonClicked(advertisementResponse.advertisement.id)
            }

            viewModel
                .loadingFlow
                .onEach {
                    progressCircular.isVisible = true
                    layoutAdvertisement.isVisible = false
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .errorFlow
                .onEach {
                    progressCircular.isVisible = false
                    layoutAdvertisement.isVisible = true
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
                    layoutAdvertisement.isVisible = true
                    AlertDialog
                        .Builder(requireContext())
                        .setTitle("Бронь")
                        .setMessage(it)
                        .setPositiveButton(android.R.string.ok, null)
                        .show()
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .bookingFlow
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}