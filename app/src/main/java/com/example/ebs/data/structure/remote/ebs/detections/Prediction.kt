package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Prediction(
    val id: String = "",
    @SerialName("bbox") val boundingBox: List<Double> = emptyList(),
    val category: String = "bukan e-waste",
    @SerialName("risk_lvl") val riskLvl: Int = 5,
    val confidence: Double = 0.0,
    val suggestion: List<String> = listOf("Hmm... Sepertinya Bukan E-Waste \uD83E\uDD14","Semoga gambar yang diunggah sesuai dengan kategori e-waste yang ada","Jika tidak, silakan unggah gambar lain yang sesuai"),
    val description: String = "Ketika aplikasi menerima gambar yang tidak dapat dikenali, maka sumber daya yang digunakan menjadi sia-sia",
    @SerialName("damage_level") val damageLvl: Int = 5,
    @SerialName("detection_source") val detSource: String = "",
    @SerialName("regression_result") val regResult: Double = 0.0,
)