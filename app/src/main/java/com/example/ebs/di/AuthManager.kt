package com.example.ebs.di

import android.content.Context
import com.example.ebs.service.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAuthManager(supabase: SupabaseClient): AuthManager {
        return AuthManager(supabase)
    }
}