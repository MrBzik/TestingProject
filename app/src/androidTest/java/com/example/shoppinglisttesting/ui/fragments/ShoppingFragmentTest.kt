package com.example.shoppinglisttesting.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
import org.mockito.Mockito.verify



@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class ShoppingFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickFabAddShoppingItem_navigateToAddShoppingItemFragment(){

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
           onView(withId(R.id.fabAddShoppingItem)).perform(click())

            verify(navController).navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )
    }

}