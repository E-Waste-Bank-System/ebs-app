package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Waste(
    val id: String = "",
    @SerialName("created_at") val createdAt: Instant = Instant.fromEpochMilliseconds(0),
    @SerialName("updated_at") val updatedAt: Instant = Instant.fromEpochMilliseconds(0),
    @SerialName("deleted_at") val deletedAt: Instant? = Instant.fromEpochMilliseconds(0),
    val name: String = "ini bukan e-waste ya? \uD83D\uDE0F",
    val category: String = "bukan e-waste",
    @SerialName("confidence_score") val confidence: Double = 0.0,
    @SerialName("bounding_box") val boundingBox: BoundingBox = BoundingBox(),
    @SerialName("estimated_value") val estValue: Double? = null,
    @SerialName("risk_level") val riskLvl: Int = 5,
    @SerialName("damage_level") val damageLvl: Int = 5,
    @SerialName("is_validated") val isValidated: Boolean = false,
    @SerialName("validated_by") val validatedBy: String? = null,
    @SerialName("validated_at") val validatedAt: Instant? = null,
    @SerialName("validation_notes") val valNotes: String? = "",
    val description: String? = "Ketika aplikasi menerima gambar yang tidak dapat dikenali, maka sumber daya yang digunakan menjadi sia-sia",
    val suggestions: List<String>? = listOf("Hmm... Sepertinya Bukan E-Waste \uD83E\uDD14","Semoga gambar yang diunggah sesuai dengan kategori e-waste yang ada","Jika tidak, silakan unggah gambar lain yang sesuai"),
    @SerialName("ai_metadata") val aiMeta: AiMetadata = AiMetadata(),
    @SerialName("scan_id") val scanId: String = "",
    val total: Int = 0
)
