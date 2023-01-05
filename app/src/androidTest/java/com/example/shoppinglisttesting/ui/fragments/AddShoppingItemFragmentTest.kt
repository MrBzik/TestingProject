package com.example.shoppinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
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
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem
import com.example.shoppinglisttesting.getOrAwaitValue
import com.example.shoppinglisttesting.repositories.FakeMainRepository
import com.example.shoppinglisttesting.ui.ShoppingFragmentFactory
import com.example.shoppinglisttesting.ui.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class AddShoppingItemFragmentTest {

    lateinit var testViewModel : ShoppingViewModel

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory : ShoppingFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
        testViewModel = ShoppingViewModel(FakeMainRepository())
    }

    @Test
    fun pressBack_navigateToShoppingFragment(){

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun clickImageView_navigatesToImagePickFragment(){

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(AddShoppingItemFragmentDirections
            .actionAddShoppingItemFragmentToImagePickFragment())
    }

    @Test
    fun verifyEmptyImageUrl_afterBackPressed(){

        val navController = mock(NavController::class.java)


        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
        }
        pressBack()
        assertThat(testViewModel.currentImageUrl.getOrAwaitValue()).isEqualTo("")
    }

    @Test
    fun insertingNewItem_canObserveIt(){

        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory) {

            viewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("apple"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("1"))

        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.observeAllItems.getOrAwaitValue())
            .contains(ShoppingItem(5, "apple", 1f, ""))
    }



}