package com.example.shoppinglisttesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppinglisttesting.getOrAwaitValue
import com.example.shoppinglisttesting.repositories.FakeMainRepository
import com.example.shoppinglisttesting.utils.Constants.MAX_ITEM_NAME_LENGTH
import com.example.shoppinglisttesting.utils.Constants.MAX_ITEM_PRICE_LENGTH
import com.example.shoppinglisttesting.utils.Status
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    lateinit var viewModel : ShoppingViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){

        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = ShoppingViewModel(FakeMainRepository())
    }

    @After
    fun teardown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `inserting shopping item with empty field returns error`(){

        viewModel.insertShoppingItem("apples", "", "1")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `inserting shopping item with too long name returns error`(){

        val name = buildString {
            for (i in 1..MAX_ITEM_NAME_LENGTH +1) {
                append("d")
            }
        }

        viewModel.insertShoppingItem(name, "5", "1")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `inserting shopping item with too long price returns error`(){

        val price = buildString {
            for (i in 1..MAX_ITEM_PRICE_LENGTH +1) {
                append("9")
            }
        }

        viewModel.insertShoppingItem("apple", "5", price)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `inserting shopping item with too high amount returns error`(){

        viewModel.insertShoppingItem("apple", "555555555555555555555555", "1")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `inserting valid shopping item returns success`(){

        viewModel.insertShoppingItem("apple", "5", "1")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `after inserting valid item resets image url`(){

        viewModel.setCurrentImageUrl("some url")

        viewModel.insertShoppingItem("apple", "5", "1")

        assertThat(viewModel.currentImageUrl.value).isEqualTo("")
    }

    @Test
    fun `is current url observable`(){

        viewModel.setCurrentImageUrl("TEST")

        assertThat(viewModel.currentImageUrl.value).isEqualTo("TEST")
    }



}