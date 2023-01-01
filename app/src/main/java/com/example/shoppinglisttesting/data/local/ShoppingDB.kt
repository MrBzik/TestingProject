package com.example.shoppinglisttesting.data.entities

import androidx.room.Database
import com.example.shoppinglisttesting.data.ShoppingDao

@Database (entities = [ShoppingItem::class],
            version = 1)
abstract class ShoppingDB {

    abstract fun getShoppingDao() : ShoppingDao



}