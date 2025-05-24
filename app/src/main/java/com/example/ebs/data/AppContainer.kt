package com.example.ebs.data

import android.R.attr.level
import android.content.Context
import com.example.ebs.BuildConfig
import com.example.ebs.data.repositories.local.LocalItemsRepository
import com.example.ebs.data.repositories.local.ItemsDatabase
import com.example.ebs.data.repositories.local.ItemsRepository
import com.example.ebs.data.repositories.remote.book.BookRepositoryInf
import com.example.ebs.data.repositories.remote.ebsApi.EBSApiService
import com.example.ebs.data.repositories.remote.ebsApi.EBSRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
    val dataTestRepository: BookRepositoryInf
}

@Module
@InstallIn(SingletonComponent::class)
object AppDataContainer {
    val BASE_URL = BuildConfig.BASE_API_URL

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(600, TimeUnit.SECONDS)
            .readTimeout(600, TimeUnit.SECONDS)
            .writeTimeout(600, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideEBSApiService(retrofit: Retrofit): EBSApiService {
        return retrofit.create(EBSApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDataTestRepository(ebsApiService: EBSApiService): EBSRepository {
        return EBSRepository(ebsApiService)
    }

    @Provides
    @Singleton
    fun provideItemsRepository(context: Context): ItemsRepository {
        return LocalItemsRepository(ItemsDatabase.getDatabase(context).itemDao())
    }

    @Provides
    @Singleton
    fun provideContext(application: android.app.Application): Context {
        return application.applicationContext
    }
}
