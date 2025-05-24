package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetectionHistory(
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    @SerialName("scan_id") val scanId: String = "",
    @SerialName("is_validated") val isValidated: Boolean = false,
    @SerialName("image_url") val imageUrl: String = "",
    val category: String = "MANA E-WASTENYA",
    val confidence: Double = 0.0,
    @SerialName("regression_result") val regressionResult: Double? = null,
    val description: String = "Ketika aplikasi menerima gambar yang tidak dapat dikenali, maka sumber daya yang digunakan menjadi sia-sia",
    val suggestion: List<String> = listOf("Hmm... Sepertinya Bukan E-Waste \uD83E\uDD14","Semoga gambar yang diunggah sesuai dengan kategori e-waste yang ada","Jika tidak, silakan unggah gambar lain yang sesuai"),
    @SerialName("risk_lvl") val riskLvl: Int = 5,
    @SerialName("detection_source") val detectionSource: String = "",
    @SerialName("created_at") val createdAt: Instant = Instant.parse("2023-10-01T00:00:00Z"),
    @SerialName("updated_at") val updatedAt: Instant = Instant.parse("2023-10-01T00:00:00Z")
)