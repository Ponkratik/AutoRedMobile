package com.ponkratov.autored.presentation.ui.home.tab.history

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ponkratov.autored.R
import com.ponkratov.autored.databinding.ItemRideBinding
import com.ponkratov.autored.domain.model.response.RideResponse
import java.text.SimpleDateFormat
import java.util.*

class RideViewHolder(
    private val binding: ItemRideBinding,
    private val onRideItemClicked: (RideResponse) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RideResponse) {
        with(binding) {
            carPhoto.load("http://10.0.2.2:8080/api/attachment/get/file/${item.advertisementResponse.photoPaths.last()}")
            carName.text = binding.root.context.getString(
                R.string.text_car_make_model_year,
                item.advertisementResponse.car.make,
                item.advertisementResponse.car.model,
                SimpleDateFormat(
                    "yyyy",
                    Locale.US
                ).format(item.advertisementResponse.car.manufacturedYear)
            )

            textDateStart.text =
                SimpleDateFormat("dd.MM.yyyy", Locale.US).format(item.ride.dateStart)

            textDateEnd.text = if (item.ride.dateEnd == Date(0)) {
                "по н.в."
            } else {
                SimpleDateFormat("dd.MM.yyyy", Locale.US).format(item.ride.dateEnd)
            }

            textPricePerDay.text =
                binding.root.context.getString(
                    R.string.text_price,
                    item.advertisementResponse.advertisement.pricePerDay
                )

            root.setOnClickListener {
                onRideItemClicked(item)
            }
        }
    }
}