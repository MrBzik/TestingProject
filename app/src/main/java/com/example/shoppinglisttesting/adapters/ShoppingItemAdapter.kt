package com.example.shoppinglisttesting.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem
import com.example.shoppinglisttesting.databinding.ItemShoppingBinding
import javax.inject.Inject

class ShoppingItemAdapter @Inject constructor (
    private val glide : RequestManager
        ) : RecyclerView.Adapter<ShoppingItemAdapter.ItemHolder>() {

    class ItemHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        var bind : ItemShoppingBinding

        init {
            bind = ItemShoppingBinding.bind(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        return ItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shopping, parent, false)
        )

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val currentItem = shoppingItems[position]

        holder.bind.apply {
            tvName.text = currentItem.name
            tvShoppingItemAmount.text = currentItem.amount.toString()

            val priceText = "${currentItem.price} euro"

            tvShoppingItemPrice.text = priceText
        }

        glide.load(currentItem.imageUrl).into(holder.bind.ivShoppingImage)


    }

    override fun getItemCount(): Int {

        return shoppingItems.size

    }

    private val diffCallBack = object : DiffUtil.ItemCallback<ShoppingItem>(){
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var shoppingItems : List<ShoppingItem>
    get() = differ.currentList
    set(value) = differ.submitList(value)

}