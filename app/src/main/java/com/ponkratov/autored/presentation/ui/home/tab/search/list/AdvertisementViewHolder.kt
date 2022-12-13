package com.ponkratov.autored.presentation.ui.home.tab.search.list

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ponkratov.autored.R
import com.ponkratov.autored.databinding.ItemAdvertisementBinding
import com.ponkratov.autored.domain.model.response.AdvertisementResponse
import java.text.SimpleDateFormat
import java.util.Locale

class AdvertisementResponseViewHolder(
    private val binding: ItemAdvertisementBinding,
    private val onAdvertisementItemClicked: (AdvertisementResponse) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AdvertisementResponse) {
        with(binding) {
            carPhoto.load("http://10.0.2.2:8080/api/attachment/get/file/${item.photoPaths.last()}")
            carName.text = binding.root.context.getString(
                R.string.text_car_make_model_year,
                item.car.make,
                item.car.model,
                SimpleDateFormat("yyyy", Locale.US).format(item.car.manufacturedYear)
            )
            //оценку добавить
            textRate.text = "5.0"
            textPricePerDay.text =
                binding.root.context.getString(R.string.text_price, item.advertisement.pricePerDay)
            //кол-во поездок
            textRidesQty.text = "10 поездок"

            root.setOnClickListener {
                onAdvertisementItemClicked(item)
            }
        }
    }
}