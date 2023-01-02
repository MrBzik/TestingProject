package com.example.shoppinglisttesting.repositories

import com.example.shoppinglisttesting.data.local.ShoppingDao
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem
import com.example.shoppinglisttesting.data.remote.PixabayApi
import com.example.shoppinglisttesting.data.remote.responses.PixabayResponse
import com.example.shoppinglisttesting.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainRepository @Inject constructor (
    private val shoppingDao: ShoppingDao,
    private val pixabayApi: PixabayApi) : ShoppingRepository {

    override suspend fun insertShoppingItem (item : ShoppingItem) = shoppingDao.upsert(item)

    override suspend fun deleteShoppingItem (item : ShoppingItem) = shoppingDao.delete(item)

    override fun observeTotalPrice () = shoppingDao.observeTotalPrice()

    override fun observeAllItems () = shoppingDao.observeAllItems()

    override suspend fun searchForImages (query : String) : Resource<PixabayResponse> {

        return try {

            val response = pixabayApi.makeRequest(searchQuery = query)
            if(response.isSuccessful){
                response.body()?.let {
                   return@let Resource.success(it)
                } ?: Resource.error("Unknown error", null)
            } else {
                    Resource.error("unknown error", null)
            }
        } catch (e : Exception) {
            return Resource.error("Could not reach the server", null)
        }
    }




}