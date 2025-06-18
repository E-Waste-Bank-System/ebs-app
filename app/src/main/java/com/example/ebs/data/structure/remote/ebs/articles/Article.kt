package com.example.ebs.data.structure.remote.ebs.articles

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: String = "",
    val title: String = "",
    val slug: String = "",
    val content: ContentArticle = ContentArticle(),
    val excerpt: String? = "",
    @SerialName("featured_image") val imageUrl: String? = "",
    val status: String = "",
    val tags: List<String>? = emptyList(),
    @SerialName("view_count") val viewCount: Int = 0,
    @SerialName("is_featured") val isFeatured: Boolean? = false,
    @SerialName("meta_title") val metaTitle: String? = "",
    @SerialName("meta_description") val metaDescription: String? = "",
    @SerialName("author_id") val authorId: String? = "",
    @SerialName("created_at") val createdAt: Instant = Instant.fromEpochMilliseconds(0),
    @SerialName("published_at") val publishedAt: Instant? = Instant.fromEpochMilliseconds(0),
    @SerialName("updated_at") val updatedAt: Instant? = Instant.fromEpochMilliseconds(0),
    @SerialName("deleted_at") val deletedAt: Instant? = Instant.fromEpochMilliseconds(0)
)
