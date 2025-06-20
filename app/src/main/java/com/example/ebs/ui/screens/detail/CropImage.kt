package com.example.ebs.ui.screens.detail

import android.graphics.Bitmap
import android.util.Log

data class Croppy(
    val bitMap: Bitmap?,
    val size: Int
)

fun cropImage(box: FloatArray, originalBitmap: Bitmap, previewSize: Int, constraint: List<Int>): Croppy? {
    Log.e("OriginalBitmap", "Width: ${originalBitmap.width}, Height: ${originalBitmap.height}")
    Log.e("BoundingBox", "Coordinates: ${box.joinToString(", ")}")
    // 1. Ensure the bounding box has the correct number of coordinates
    if (box.size < 4) {
        Log.e("BitmapCrop", "Invalid bounding box: Not enough coordinates.")
        return null
    }


//    var scalex: Float = originalBitmap.width.toFloat() / previewSize
//    var scaley: Float = originalBitmap.height.toFloat() / previewSize
    var size = previewSize

//    var scale = size * 0.00093
    var scale = 1

    // 2. Extract original YOLO coordinates and scale them
    var x = box[0] * scale
    var y = box[1] * scale
    var width = box[2] * scale
    var height = box[3] * scale

//    if(x + width > constraint[0] || y + height > constraint[1]) {
//        while (x + width > constraint[0] || y + height > constraint[1]) {
//            size--
//            scale = size * 0.00093056
//
//            x = box[0] * scale
//            width = box[2] * scale
//
//            y = box[1] * scale
//            height = box[3] * scale
//        }
//        size -= (size * 0.17).toInt()
//        scale = size * 0.00093056
//
//        x = box[0] * scale
//        width = box[2] * scale
//
//        y = box[1] * scale
//        height = box[3] * scale
//    }

    Log.e("BitmapCrop", "Scaled Coordinates: x = $scale size = $size")

    Log.e("BitmapCrop", "x($x) + width($width) = ${x+width} > bitmapWidth(${originalBitmap.width} ${constraint[0]})")
    Log.e("BitmapCrop", "y($y) + height($height) = ${y+height} > bitmapHeight(${originalBitmap.height} ${constraint[1]})")

    // 4. **CRITICAL** Safety Checks to prevent crashes
    // a) Check for negative or zero dimensions
    if (width <= 0 || height <= 0) {
        Log.e(
            "BitmapCrop",
            "Invalid bounding box: Calculated width or height is zero or negative. Width: $width, Height: $height"
        )
        return null
    }
    // b) Check if the crop area is within the original bitmap's bounds
    if (x < 0 || y < 0 || x + width > originalBitmap.width || y + height > originalBitmap.height) {
        Log.e("BitmapCrop", "Invalid bounding box: Crop area is outside the bitmap bounds.")
        // You can add more detailed logging here to see which check failed.
        return null
    }

    Log.e("BitmapCrop", "Crop area: Good")

    // 5. Create the new bitmap using the calculated crop area
    return try {
        Croppy(
            Bitmap.createBitmap(originalBitmap, x.toInt(), y.toInt(), width.toInt(), height.toInt()),
            size
        )
    } catch (e: IllegalArgumentException) {
        Log.e("BitmapCrop", "Failed to create bitmap: ${e.message}", e)
        null
    }
}