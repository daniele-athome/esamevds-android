package it.casaricci.esamevds

import android.content.Context
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.max
import kotlin.math.pow

object Utils {

    fun resizeBitmap(context: Context, imageBitmap: Bitmap): Bitmap {

        val heightbmp = imageBitmap.height.toFloat()
        val widthbmp = imageBitmap.width.toFloat()

        // Get Screen width
        val displaymetrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(displaymetrics)
        val height = displaymetrics.heightPixels.toFloat()
        val width = displaymetrics.widthPixels.toFloat()

        val maxImageSize = (height * width).toInt()
        var scale = 1
        while (widthbmp * heightbmp * (1 / scale.toDouble().pow(2.0)) > maxImageSize)
            scale++

        val scaleFactor = max(widthbmp / width, heightbmp / height)
        val w = (widthbmp / scaleFactor).toInt()
        val h = (heightbmp / scaleFactor).toInt()

        return Bitmap.createScaledBitmap(imageBitmap, w, h, true)
    }

    fun normalizeDegrees(degrees: Int): Int {
        return ((degrees % 360) + 360) % 360
    }

    fun invertDirection(degrees: Int) : Int {
        return normalizeDegrees(degrees + 180)
    }

    fun floorMod(x: Int, y: Int): Int {
        return x - floorDiv(x, y) * y
    }

    fun floorDiv(x: Int, y: Int): Int {
        var r = x / y
        // if the signs are different and modulo not zero, round down
        if (x xor y < 0 && r * y != x) {
            r--
        }
        return r
    }

}
