package com.example.ebs.ui.components.texts

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle

fun checkTag(text: Any): AnnotatedString {
    val str = text.toString()
    val annotated = when {
        str.contains("<i>") && str.contains("</i>") -> {
            val start = str.indexOf("<i>") + 3
            val end = str.indexOf("</i>")
            buildAnnotatedString {
                append(str.substring(0, str.indexOf("<i>")))
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    append(str.substring(start, end))
                }
                append(str.substring(end + 4))
            }
        }
        (str.contains("<b>") && str.contains("</b>")) -> {
            val start = str.indexOf("<b>") + 3
            val end = str.indexOf("</b>")
            buildAnnotatedString {
                append(str.substring(0, str.indexOf("<b>")))
                withStyle(SpanStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) {
                    append(str.substring(start, end))
                }
                append(str.substring(end + 4))
            }
        }
        (str.contains("<strong>") && str.contains("</strong>")) -> {
            val start = str.indexOf("<strong>") + 8
            val end = str.indexOf("</strong>")
            buildAnnotatedString {
                append(str.substring(0, str.indexOf("<strong>")))
                withStyle(SpanStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) {
                    append(str.substring(start, end))
                }
                append(str.substring(end + 9))
            }
        }
        str.contains("**") -> {
            val start = str.indexOf("**") + 2
            val end = str.indexOf("**", start)
            if (end > start) {
                buildAnnotatedString {
                    append(str.substring(0, str.indexOf("**")))
                    withStyle(SpanStyle(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) {
                        append(str.substring(start, end))
                    }
                    append(str.substring(end + 2))
                }
            } else {
                buildAnnotatedString {
                    append(str)
                }
            }
        }
        else -> {
            return if (text is String) AnnotatedString(text) else text as AnnotatedString
        }
    }
    return annotated
}