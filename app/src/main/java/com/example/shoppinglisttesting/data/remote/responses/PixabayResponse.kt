package com.example.shoppinglisttesting.data.remote.responses

data class PixabayResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)