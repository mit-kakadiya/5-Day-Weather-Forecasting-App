package com.example.evalutionpractical.di

import android.content.Context
import androidx.room.Room
import com.example.RoomDataBase.AppDatabase
import com.example.evalutionpractical.database.Dao.WeatherDetailsDao
import com.example.evalutionpractical.Network.ApiConstants
import com.example.evalutionpractical.Network.ApiService
import com.example.evalutionpractical.database.Dao.AllWeatherDetailsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun getDataBaseInstance(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "WeatherDetails")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase): WeatherDetailsDao {
        return db.weatherDetailsDao()
    }

    @Provides
    @Singleton
    fun provideDaoForAllData(db: AppDatabase):AllWeatherDetailsDao{
        return db.allDetailsDao()
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return ApiConstants.BASE_URL
    }

    @Provides
    @Singleton
    fun loggingInterceptorProvides(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun okHttpClientProvides(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cache(null)
            .build()
    }

    @Provides
    @Singleton
    fun retrofitProvides(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(provideBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun apiServiceProvides(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}