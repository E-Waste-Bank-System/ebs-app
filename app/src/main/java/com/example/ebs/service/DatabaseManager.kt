package com.example.ebs.service

import android.util.Log
import com.example.ebs.data.structure.Articles
import com.example.ebs.data.structure.Requests
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import javax.inject.Inject

class DatabaseManager @Inject constructor(
    private val supabase: SupabaseClient
) {
    suspend fun getArticles(): List<Articles> {
        return try {
            val articles = supabase.from("articles")
                .select()
                .decodeList<Articles>()
            articles
        } catch (e: Exception) {
            Log.e("Gagal","Inih article gaga $e")
            emptyList()
        }
    }

    suspend fun getRequests(): List<Requests> {
        return try {
            val requests = supabase.from("requests")
                .select()
                .decodeList<Requests>()
            requests
        } catch (e: Exception) {
            Log.e("Gagal","Inih request gagal $e")
            emptyList()
        }
    }
}
