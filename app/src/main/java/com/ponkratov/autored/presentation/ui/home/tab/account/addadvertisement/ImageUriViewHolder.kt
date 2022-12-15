package com.ponkratov.autored.presentation.ui.home.tab.account.addadvertisement

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.ponkratov.autored.databinding.ItemImageBinding

class ImageUriViewHolder(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(uri: Uri) {
        with(binding) {
            carPhoto.setImageURI(uri)
        }
    }
}