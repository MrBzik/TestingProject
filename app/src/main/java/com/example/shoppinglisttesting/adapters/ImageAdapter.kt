package com.example.shoppinglisttesting.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.databinding.ItemImageBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor(private val glide : RequestManager)
    : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    class ImageHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var bind : ItemImageBinding
        init {
            bind = ItemImageBinding.bind(itemView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {

       return ImageHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)
        )

    }

    private var onClickListener : ((String) -> Unit)? = null

    fun setOnClickListener(listener : (String) -> Unit) {
        onClickListener = listener
    }


    override fun onBindViewHolder(holder: ImageHolder, position: Int) {

        val currentUrl = images[position]

        glide.load(currentUrl).into(holder.bind.ivShoppingImage)

        holder.itemView.setOnClickListener {
            onClickListener?.apply {
                this(currentUrl)
            }
        }

    }

    override fun getItemCount(): Int {
       return images.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

        var images : List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)

}