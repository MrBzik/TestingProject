package com.example.shoppinglisttesting.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.filters.MediumTest
import com.example.shoppinglisttesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.adapters.ImageAdapter
import com.example.shoppinglisttesting.getOrAwaitValue
import com.example.shoppinglisttesting.repositories.FakeMainRepository
import com.example.shoppinglisttesting.ui.ShoppingFragmentFactory
import com.example.shoppinglisttesting.ui.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import org.mockito.Mockito.verify
import javax.inject.Inject


@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun adapterClickListener_popBackAndSetImageUrl(){

        val navController = mock(NavController::class.java)
        val testViewModel = ShoppingViewModel(FakeMainRepository())
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images = listOf("TEST")
            viewModel = testViewModel
        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageHolder>(
                0, click())
        )

        verify(navController).popBackStack()
        assertThat(testViewModel.currentImageUrl.getOrAwaitValue()).isEqualTo("TEST")

    }



}