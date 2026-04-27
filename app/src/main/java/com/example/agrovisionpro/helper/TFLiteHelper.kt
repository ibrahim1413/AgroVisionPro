package com.example.agrovisionpro.helper

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TFLiteHelper(context: Context) {

    private var interpreter: Interpreter

    private val labels = listOf(
        "bacterial_leaf_blight",
        "brown_spot",
        "healthy",
        "leaf_blast",
        "leaf_scald",
        "narrow_brown_spot"
    )

    init {
        val model = context.assets.open("crop_model.tflite").readBytes()

        val buffer = ByteBuffer.allocateDirect(model.size)
        buffer.order(ByteOrder.nativeOrder())
        buffer.put(model)
        buffer.rewind()

        interpreter = Interpreter(buffer)
    }

    fun predict(bitmap: Bitmap): String {

        val resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

        val input =
            ByteBuffer.allocateDirect(4 * 224 * 224 * 3).order(ByteOrder.nativeOrder())

        for (y in 0 until 224) {
            for (x in 0 until 224) {

                val px = resized.getPixel(x, y)

                input.putFloat(((px shr 16 and 0xFF) / 255f))
                input.putFloat(((px shr 8 and 0xFF) / 255f))
                input.putFloat(((px and 0xFF) / 255f))
            }
        }

        val output = Array(1) { FloatArray(6) }

        interpreter.run(input, output)

        val result = output[0]
        val maxIndex = result.indices.maxByOrNull { result[it] } ?: 0

        return labels[maxIndex]
    }
}