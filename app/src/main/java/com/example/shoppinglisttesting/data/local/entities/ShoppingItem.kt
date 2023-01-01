package com.example.shoppinglisttesting.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "shopping_items")
data class ShoppingItem (

    var amount : Int,
    var name : String,
    var price : Float

        ) {
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null

}