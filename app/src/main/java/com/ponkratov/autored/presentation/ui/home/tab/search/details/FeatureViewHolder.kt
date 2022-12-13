package com.ponkratov.autored.presentation.ui.home.tab.search.details

import androidx.recyclerview.widget.RecyclerView
import com.ponkratov.autored.databinding.ItemCarFeatureBinding

class FeatureViewHolder(
  private val binding: ItemCarFeatureBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: String) {
        with(binding) {
            textFeature.text = item
        }
    }
}