package com.ponkratov.autored.presentation.ui.home.tab.account.ridelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ponkratov.autored.databinding.ItemRideBinding
import com.ponkratov.autored.databinding.ItemRideLesseeBinding
import com.ponkratov.autored.domain.model.response.RideResponse

class RideLesseeAdapter(
    context: Context,
    private val onRideClicked: (RideResponse) -> Unit
) : ListAdapter<RideResponse, RideLesseeViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RideLesseeViewHolder {
        return RideLesseeViewHolder(
            binding = ItemRideLesseeBinding.inflate(layoutInflater, parent, false),
            onRideItemClicked = onRideClicked
        )
    }

    override fun onBindViewHolder(holder: RideLesseeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<RideResponse>() {
            override fun areItemsTheSame(
                oldItem: RideResponse,
                newItem: RideResponse
            ): Boolean {
                return oldItem.ride.id == newItem.ride.id
            }

            override fun areContentsTheSame(
                oldItem: RideResponse,
                newItem: RideResponse
            ): Boolean {
                return oldItem.ride.id == newItem.ride.id
            }

        }
    }
}