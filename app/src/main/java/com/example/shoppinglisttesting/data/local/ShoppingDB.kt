package com.example.shoppinglisttesting.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem

@Database (entities = [ShoppingItem::class],
            version = 1)
abstract class ShoppingDB : RoomDatabase(){

    abstract fun getShoppingDao() : ShoppingDao



}