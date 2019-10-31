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

}
