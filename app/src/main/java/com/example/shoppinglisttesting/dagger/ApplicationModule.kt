package com.example.shoppinglisttesting.dagger

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.request.RequestOptions
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.data.local.ShoppingDB
import com.example.shoppinglisttesting.data.local.ShoppingDao
import com.example.shoppinglisttesting.data.remote.PixabayApi
import com.example.shoppinglisttesting.repositories.MainRepository
import com.example.shoppinglisttesting.repositories.ShoppingRepository
import com.example.shoppinglisttesting.utils.Constants.DATABASE_NAME
import com.example.shoppinglisttesting.utils.Constants.PIXABAY_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providesShoppingDB (
           @ApplicationContext app : Context
    ) : ShoppingDB = Room.databaseBuilder(app, ShoppingDB::class.java, DATABASE_NAME).build()


    @Provides
    @Singleton
    fun providesShoppingDao(db : ShoppingDB) = db.getShoppingDao()

    @Provides
    @Singleton
    fun providesPixabayApi() : PixabayApi {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PIXABAY_BASE_URL)
            .build()
            .create(PixabayApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMainRepository(
        dao: ShoppingDao,
        api: PixabayApi
    ) = MainRepository(dao, api) as ShoppingRepository

    @Provides
    @Singleton
    fun providesGlideInstance (
        @ApplicationContext app : Context
    ) = Glide.with(app).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )


}