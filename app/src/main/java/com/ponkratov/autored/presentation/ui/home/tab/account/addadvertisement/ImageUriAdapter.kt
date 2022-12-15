package com.ponkratov.autored.presentation.ui.home.tab.account.addadvertisement

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ponkratov.autored.databinding.ItemImageBinding

class ImageUriAdapter(
    context: Context
) : ListAdapter<Uri, ImageUriViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageUriViewHolder {
        return ImageUriViewHolder(
            binding = ItemImageBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ImageUriViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }

        }
    }
}