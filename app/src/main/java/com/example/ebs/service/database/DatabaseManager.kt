package com.example.ebs.service.database

import android.util.Log
import com.example.ebs.data.structure.remote.ebs.articles.Article
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class DatabaseManager @Inject constructor(
    private val supabase: SupabaseClient
) {
    suspend fun getArticles(): List<Article> {
        return try {
            val articles = supabase.from("articles")
                .select()
                .decodeList<Article>()
            articles
        } catch (e: Exception) {
            Log.e("Gagal","Inih article gaga $e")
            emptyList()
        }
    }

    suspend fun updateArticleViewCount(id: String, viewCount: Int) {
        try {
            supabase.postgrest.from("articles").update(
                {
                    set("view_count", viewCount+1)
                }
            ) {
                filter {
                    eq("id", id)
                }
            }
        } catch (e: Exception) {
            Log.e("Gagal","Inih update article gaga $e")
        }
    }
}
