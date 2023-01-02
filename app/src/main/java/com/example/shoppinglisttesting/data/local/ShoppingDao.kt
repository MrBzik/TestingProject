package com.example.shoppinglisttesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert (item : ShoppingItem)

    @Delete
    suspend fun delete (item : ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun observeAllItems() : LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun observeTotalPrice() : LiveData<Float>


}