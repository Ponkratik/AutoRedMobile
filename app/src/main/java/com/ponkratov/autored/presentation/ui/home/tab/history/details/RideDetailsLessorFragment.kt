package com.ponkratov.autored.presentation.ui.home.tab.history.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.ponkratov.autored.R
import com.ponkratov.autored.databinding.FragmentRideDetailsLessorBinding
import com.ponkratov.autored.domain.model.response.RideResponse
import com.ponkratov.autored.presentation.extensions.addHorisontalSpace
import com.ponkratov.autored.presentation.ui.home.tab.search.details.ImageAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class RideDetailsLessorFragment : Fragment() {

    private var _binding: FragmentRideDetailsLessorBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<RideDetailsLessorViewModel>()

    private val args by navArgs<RideDetailsLessorFragmentArgs>()

    private val imageAdapter by lazy {
        ImageAdapter(
            context = requireContext()
        )
    }

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

        with(binding) {
            progressCircular.isVisible = false
            layoutRide.isVisible = true

            photoRecyclerView.adapter = imageAdapter
            photoRecyclerView.addHorisontalSpace()

            val rideResponse =
                Gson().fromJson(args.rideResponse, RideResponse::class.java)

            initButtons(rideResponse)

            imageAdapter.submitList(rideResponse.advertisementResponse.photoPaths.sortedDescending())

            carName.text = getString(
                R.string.text_car_make_model_year,
                rideResponse.advertisementResponse.car.make,
                rideResponse.advertisementResponse.car.model,
                SimpleDateFormat(
                    "yyyy",
                    Locale.US
                ).format(rideResponse.advertisementResponse.car.manufacturedYear)
            )

            textPricePerDay.text =
                getString(
                    R.string.text_price,
                    rideResponse.advertisementResponse.advertisement.pricePerDay
                )
            textPricePerWeek.text =
                getString(
                    R.string.text_price,
                    rideResponse.advertisementResponse.advertisement.pricePerWeek
                )
            textPricePerMonth.text =
                getString(
                    R.string.text_price,
                    rideResponse.advertisementResponse.advertisement.pricePerMonth
                )

            textLinkChat.text = rideResponse.user.email
            textLinkPayment.text = "Перейти"

            textReview.setOnClickListener {
                findNavController().navigate(
                    RideDetailsLessorFragmentDirections.actionDetailsToReview(
                        rideResponse.advertisementResponse.advertisement.userId,
                        rideResponse.advertisementResponse.car.id
                    )
                )
            }

            textLinkPayment.setOnClickListener {
                findNavController().navigate(RideDetailsLessorFragmentDirections.actionDetailsToPayment())
            }

            buttonBack.setOnClickListener {
                findNavController().navigateUp()
            }

            buttonSignAct.setOnClickListener {
                AlertDialog
                    .Builder(requireContext())
                    .setTitle("Подписание акта")
                    .setMessage("Нажимая на кнопку \"ОК\" вы подтверждаете ознакомление с условиями сервиса")
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
            }

            buttonStartRide.setOnClickListener {
                AlertDialog
                    .Builder(requireContext())
                    .setTitle("Начало поездки")
                    .setMessage("Нажимая на кнопку \"ОК\" вы подтверждаете ознакомление с условиями сервиса")
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
            }

            buttonEndRide.setOnClickListener {
                AlertDialog
                    .Builder(requireContext())
                    .setTitle("Завершение поездки")
                    .setMessage("Нажимая на кнопку \"ОК\" вы подтверждаете ознакомление с условиями сервиса")
                    .setPositiveButton(android.R.string.ok, null)
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
            }
        }

    }

    private fun initButtons(rideResponse: RideResponse) {
        with(binding) {
            if (rideResponse.ride.dateStart == Date(0)) {
                textReview.isVisible = false
                descriptionTable.isVisible = true
                buttonSignAct.isVisible = true
                buttonStartRide.isVisible = false
                buttonEndRide.isVisible = false
                textStatus.text = "Автомобиль забронирован"
                return
            }

            if (rideResponse.ride.dateStart == Date(0)
                && rideResponse.ride.dateSignedLessor != Date(0)
                && rideResponse.ride.dateSignedLessee == Date(0)) {
                textReview.isVisible = false
                descriptionTable.isVisible = true
                buttonSignAct.isVisible = false
                buttonStartRide.isVisible = false
                buttonEndRide.isVisible = false
                textStatus.text = "Ожидание подписания акта арендодателем"
                return
            }

            if (rideResponse.ride.dateStart == Date(0)
                && rideResponse.ride.dateSignedLessor != Date(0)
                && rideResponse.ride.dateSignedLessee != Date(0)) {
                textReview.isVisible = false
                descriptionTable.isVisible = true
                buttonSignAct.isVisible = false
                buttonStartRide.isVisible = true
                buttonEndRide.isVisible = false
                textStatus.text = "Акт подписан"
                return
            }

            if (rideResponse.ride.dateStart != Date(0)
                && rideResponse.ride.dateEnd == Date(0)) {
                textReview.isVisible = false
                descriptionTable.isVisible = true
                buttonSignAct.isVisible = false
                buttonStartRide.isVisible = false
                buttonEndRide.isVisible = true
                textStatus.text = "Поездка начата"
                return
            }

            if (rideResponse.ride.dateEnd != Date(0)) {
                textReview.isVisible = true
                descriptionTable.isVisible = false
                buttonSignAct.isVisible = false
                buttonStartRide.isVisible = false
                buttonEndRide.isVisible = false
                textStatus.text = "Поездка завершена"
                return
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}