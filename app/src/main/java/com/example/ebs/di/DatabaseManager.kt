package com.example.ebs.di

import com.example.ebs.service.database.DatabaseManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDatabaseManager(supabase: SupabaseClient): DatabaseManager {
        return DatabaseManager(supabase)
    }
}