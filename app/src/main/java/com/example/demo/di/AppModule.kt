package com.example.demo.di

import android.content.Context
import androidx.room.Room
import com.example.demo.cons.Cons.BASE_URL
import com.example.demo.database.AlertDao
import com.example.demo.database.AlertDatabase
import com.example.demo.database.AlertRepository
import com.example.demo.network.ApiService
import com.example.demo.network.BasicAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesAlertDao(userDatabase: AlertDatabase):AlertDao = userDatabase.userDao()

    @Provides
    @Singleton
    fun providesAlertDatabase(@ApplicationContext context: Context):AlertDatabase
            = Room.databaseBuilder(context,AlertDatabase::class.java,"AlertDatabase").allowMainThreadQueries().build()

    @Provides
    fun providesUserRepository(userDao: AlertDao) : AlertRepository
            = AlertRepository(userDao)

    @Provides
    fun providesUrlTest() = BASE_URL

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(BasicAuthInterceptor("PortOfOakland", "OaklandPortal@2022!*"))
            .build()

    @Provides
    @Singleton
    fun providesApiService(okHttpClient: OkHttpClient,url: String): ApiService =
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}