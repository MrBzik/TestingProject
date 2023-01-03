package com.example.shoppinglisttesting.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem
import com.example.shoppinglisttesting.data.remote.responses.PixabayResponse
import com.example.shoppinglisttesting.repositories.ShoppingRepository
import com.example.shoppinglisttesting.utils.Event
import com.example.shoppinglisttesting.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
            private val repository: ShoppingRepository
) : ViewModel() {

    val observeAllItems = repository.observeAllItems()

    val observeTotalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<PixabayResponse>>>()
    val images : LiveData<Event<Resource<PixabayResponse>>> = _images

    private val _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl : LiveData<String> = _currentImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus : LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus


    fun deleteShoppingItem(item : ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(item)
    }

    fun insertShoppingItemIntoDB(item : ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(item)
    }

    fun setCurrentImageUrl(url : String){
        _currentImageUrl.postValue(url)
    }

    //fun to validate user input
    fun insertShoppingItem(name : String, amountString : String, priceString : String){

    }

    fun searchForImages(query : String){}

}