package com.example.shoppinglisttesting.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem
import com.example.shoppinglisttesting.data.remote.responses.PixabayResponse
import com.example.shoppinglisttesting.repositories.ShoppingRepository
import com.example.shoppinglisttesting.utils.Constants.MAX_ITEM_NAME_LENGTH
import com.example.shoppinglisttesting.utils.Constants.MAX_ITEM_PRICE_LENGTH
import com.example.shoppinglisttesting.utils.Event
import com.example.shoppinglisttesting.utils.Resource
import com.example.shoppinglisttesting.utils.Status
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
    fun insertShoppingItem(name : String, amountString : String, priceString : String) {

        if(name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            val status = Event(Resource.error("empty field", null))
            _insertShoppingItemStatus.postValue(status)
            return
        }

        if(name.length > MAX_ITEM_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(Resource.error("name is too long", null))
            )
            return
        }

        if(priceString.length > MAX_ITEM_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(
                Event(Resource.error("price is too big", null))
            )
            return
        }

        val amount =  try {

            amountString.toInt()

        } catch (e : NumberFormatException) {
            _insertShoppingItemStatus.postValue(
                Event(Resource.error("amount is too high", null)))
            return
        }

        val shoppingItem = ShoppingItem(amount, name, priceString.toFloat(),
                        _currentImageUrl.value ?: "")

        insertShoppingItemIntoDB(shoppingItem)

        setCurrentImageUrl("")

        _insertShoppingItemStatus.postValue(
            Event(Resource.success(shoppingItem))
        )

    }

    fun searchForImages(query : String){
        if(query.isEmpty()) {
            return
        }
        else{
          _images.value = Event(Resource.loading(null))
            viewModelScope.launch{
               val response = repository.searchForImages(query)
               _images.value = Event(response)
            }
        }
    }


}