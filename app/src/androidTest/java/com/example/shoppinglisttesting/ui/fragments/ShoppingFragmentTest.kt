package com.example.shoppinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.filters.MediumTest
import com.example.shoppinglisttesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.adapters.ImageAdapter
import com.example.shoppinglisttesting.adapters.ShoppingItemAdapter
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem
import com.example.shoppinglisttesting.getOrAwaitValue
import com.example.shoppinglisttesting.ui.ShoppingViewModel
import com.example.shoppinglisttesting.ui.TestShoppingFragmentFactory
import com.google.common.truth.Truth.assertThat
import org.mockito.Mockito.verify
import javax.inject.Inject


@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class ShoppingFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Inject
    lateinit var testFragmentFactory : TestShoppingFragmentFactory


    @Test
    fun clickFabAddShoppingItem_navigateToAddShoppingItemFragment(){

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = testFragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }
           onView(withId(R.id.fabAddShoppingItem)).perform(click())

            verify(navController).navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )
    }

    @Test
    fun swipeShoppingItem_deleteItemInDB(){

        val item = ShoppingItem(5, "banana", 2f, "")
        val item2 = ShoppingItem(1, "mela", 1f, "")

        var testViewModel : ShoppingViewModel? = null

        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = testFragmentFactory) {
            testViewModel = viewModel
            viewModel.insertShoppingItemIntoDB(item)
            viewModel.insertShoppingItemIntoDB(item2)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ItemHolder>(
                0, swipeLeft()
            )
        )

        assertThat(testViewModel?.observeAllItems?.getOrAwaitValue()).doesNotContain(item)

    }

}