package com.example.shoppinglisttesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.shoppinglisttesting.data.local.entities.ShoppingItem
import com.example.shoppinglisttesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database : ShoppingDB

    private lateinit var shoppingDao : ShoppingDao

    @Before
    fun setup(){
        hiltRule.inject()
        shoppingDao = database.getShoppingDao()
    }

    @After
    fun teardown(){

        database.close()
    }


    @Test
    fun correctInsertion(){

        val item = ShoppingItem(5, "Apple", 0.8f, "some)url", 1)
       runTest {
           shoppingDao.upsert(item)
       }

        val allShoppingItems = shoppingDao.observeAllItems().getOrAwaitValue()
        assertThat(allShoppingItems).contains(item)
    }

    @Test
    fun deleteItem(){

        val item = ShoppingItem(5, "Apple", 0.8f, "some)url", 1)
        runTest {
            shoppingDao.upsert(item)
            shoppingDao.delete(item)

            val allShoppingItem = shoppingDao.observeAllItems().getOrAwaitValue()
            assertThat(allShoppingItem).doesNotContain(item)
        }
    }

    @Test
    fun observeTotalPrice(){

        val item = ShoppingItem(5, "Apple", 1f, "some)url", 1)
        val item2 = ShoppingItem(1, "Apple", 10f, "some)url", 2)

        runTest {
            shoppingDao.upsert(item)
            shoppingDao.upsert(item2)


            val totalPrice = shoppingDao.observeTotalPrice().getOrAwaitValue()
            assertThat(totalPrice).isEqualTo(15f)
        }
    }

}