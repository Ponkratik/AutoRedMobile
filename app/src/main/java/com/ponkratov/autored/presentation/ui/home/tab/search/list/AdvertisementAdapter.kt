package com.ponkratov.autored.presentation.ui.home.tab.search.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ponkratov.autored.databinding.ItemAdvertisementBinding
import com.ponkratov.autored.domain.model.response.AdvertisementResponse

class AdvertisementAdapter(
    context: Context,
    private val onAdvertisementClicked: (AdvertisementResponse) -> Unit
) : ListAdapter<AdvertisementResponse, AdvertisementResponseViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdvertisementResponseViewHolder {
        return AdvertisementResponseViewHolder(
            binding = ItemAdvertisementBinding.inflate(layoutInflater, parent, false),
            onAdvertisementItemClicked = onAdvertisementClicked
        )
    }

    override fun onBindViewHolder(holder: AdvertisementResponseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<AdvertisementResponse>() {
            override fun areItemsTheSame(
                oldItem: AdvertisementResponse,
                newItem: AdvertisementResponse
            ): Boolean {
                return oldItem.advertisement.id == newItem.advertisement.id
            }

            override fun areContentsTheSame(
                oldItem: AdvertisementResponse,
                newItem: AdvertisementResponse
            ): Boolean {
                return oldItem.advertisement.id == newItem.advertisement.id
            }

        }
    }
}