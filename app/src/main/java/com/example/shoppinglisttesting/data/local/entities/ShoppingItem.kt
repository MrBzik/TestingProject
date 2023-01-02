package com.example.shoppinglisttesting.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "shopping_items")
data class ShoppingItem (

    var amount : Int,
    var name : String,
    var price : Float,
    var imageUrl : String,

    @PrimaryKey(autoGenerate = true)
     val id : Int? = null

        )


