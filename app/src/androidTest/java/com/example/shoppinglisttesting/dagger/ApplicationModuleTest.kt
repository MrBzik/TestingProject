package com.example.shoppinglisttesting.dagger

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.shoppinglisttesting.data.local.ShoppingDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModuleTest {

    @Provides
    @Named("test_db")
    fun providesInMemoryDB (@ApplicationContext app : Context)
        = Room.inMemoryDatabaseBuilder(
    app,
    ShoppingDB::class.java
    ).allowMainThreadQueries().build()



}

