package com.example.ebs.di

import com.example.ebs.service.auth.AuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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