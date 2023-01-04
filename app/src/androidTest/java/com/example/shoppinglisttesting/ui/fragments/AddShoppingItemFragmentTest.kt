package com.example.shoppinglisttesting.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.filters.MediumTest
import com.example.shoppinglisttesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import com.example.shoppinglisttesting.R
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.shoppinglisttesting.repositories.FakeMainRepository
import com.example.shoppinglisttesting.ui.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class AddShoppingItemFragmentTest {

    lateinit var testViewModel : ShoppingViewModel

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
        testViewModel = ShoppingViewModel(FakeMainRepository())
    }

    @Test
    fun pressBack_navigateToShoppingFragment(){

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun clickImageView_navigatesToImagePickFragment(){

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(AddShoppingItemFragmentDirections
            .actionAddShoppingItemFragmentToImagePickFragment())
    }

    @Test
    fun verifyEmptyImageUrl_afterBackPressed(){

        val navController = mock(NavController::class.java)


        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }
        pressBack()
        assertThat(testViewModel.currentImageUrl.value).isEqualTo("")


    }



}