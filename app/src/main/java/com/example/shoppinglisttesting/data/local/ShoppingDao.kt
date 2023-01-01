package com.example.shoppinglisttesting.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppinglisttesting.data.entities.ShoppingItem

@Dao
abstract class ShoppingDao {

    @Insert()
    suspend fun upsert (item : ShoppingItem) {}

    @Delete
    suspend fun delete (item : ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun getAllItems() : LiveData<List<ShoppingItem>>


}