package com.ponkratov.autored.presentation.ui.home.tab.search.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.gson.Gson
import com.ponkratov.autored.R
import com.ponkratov.autored.databinding.FragmentAdvertisementDetailsBinding
import com.ponkratov.autored.domain.model.response.AdvertisementResponse
import com.ponkratov.autored.domain.model.toListOptions
import com.ponkratov.autored.presentation.extensions.addHorisontalSpace
import java.text.SimpleDateFormat
import java.util.*

class AdvertisementDetailsFragment : Fragment() {

    private var _binding: FragmentAdvertisementDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}