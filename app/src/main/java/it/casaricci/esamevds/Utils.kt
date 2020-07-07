/*
 * EsameVDS Android app
 * Copyright (c) 2020 Daniele Ricci
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
