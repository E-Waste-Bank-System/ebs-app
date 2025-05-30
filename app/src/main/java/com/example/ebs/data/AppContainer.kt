package com.example.ebs.data

import android.content.Context
import com.example.ebs.data.repositories.LocalItemsRepository
import com.example.ebs.data.repositories.RemoteEBSRepository
import com.example.ebs.data.repositories.local.ItemsDatabase
import com.example.ebs.data.repositories.local.ItemsRepository
import com.example.ebs.data.repositories.remote.DataTestRepository
import com.example.ebs.data.repositories.remote.EBSApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
    val dataTestRepository: DataTestRepository
}

/**
 * [AppContainer] implementation that provides instance of [LocalItemsRepository]
 */
@Module
@InstallIn(SingletonComponent::class)
object AppDataContainer {
    val BASE_URL = "https://ebs-api-981332637673.asia-southeast2.run.app/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }

    /**
     * Retrofit service object for creating api calls
     */
    @Provides
    @Singleton
    fun provideEBSApiService(retrofit: Retrofit): EBSApiService {
        return retrofit.create(EBSApiService::class.java)
    }

    /**
     * DI implementation for Amphibians repository
     */
    @Provides
    @Singleton
    fun provideDataTestRepository(ebsApiService: EBSApiService): DataTestRepository {
        return RemoteEBSRepository(ebsApiService)
    }

    /**
     * Implementation for [ItemsRepository]
     */
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
