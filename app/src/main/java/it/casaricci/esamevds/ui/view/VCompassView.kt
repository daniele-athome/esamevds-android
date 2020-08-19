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

package it.casaricci.esamevds.ui.view

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*

class VCompassView: View {

    // any random degree divisible by 30
    //private val degStart = Random().nextInt(12) * 30
    // any random degree divisible by 5
    private val degStart = Random().nextInt(72) * 5

    private lateinit var numDegText: Paint
    private lateinit var smallDegLine: Paint
    private lateinit var bigDegLine: Paint

    constructor(context: Context): super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        numDegText = Paint().apply {
            color = Color.BLACK
            textSize = dpToPx(context, 35).toFloat()
            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }

        smallDegLine = Paint().apply {
            color = Color.BLACK
            strokeWidth = dpToPx(context, 5).toFloat()
        }

        bigDegLine = Paint().apply {
            color = Color.BLACK
            strokeWidth = dpToPx(context, 5).toFloat()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        // left+right: 5 degrees (10 lines, 5 big + 5 small)

        canvas?.let { c ->
            // start from center
            val posX = width / 2
            when {
                degStart % 10 == 0 -> {
                    c.drawLine(posX.toFloat(), dpToPx(context, 50).toFloat(), posX.toFloat(), dpToPx(context, 75).toFloat(), bigDegLine)
                }
                degStart % 5 == 0 -> {
                    c.drawLine(posX.toFloat(), dpToPx(context, 62).toFloat(), posX.toFloat(), dpToPx(context, 75).toFloat(), smallDegLine)
                }
                else -> {
                    throw UnsupportedOperationException("Degrees must be divisible by either 5 or 10")
                }
            }

            if (degStart % 30 == 0) {
                drawDegreeText(c, posX, degStart)
            }

            val offset = dpToPx(context, 15)

            // lines to the left (increase degrees)
            var posXl = posX - offset
            var degrees = degStart + 5
            while (posXl >= 0) {
                when {
                    degrees % 10 == 0 -> {
                        c.drawLine(posXl.toFloat(),
                            dpToPx(context, 50).toFloat(),
                            posXl.toFloat(),
                            dpToPx(context, 75).toFloat(),
                            bigDegLine)
                    }
                    degrees % 5 == 0 -> {
                        c.drawLine(posXl.toFloat(),
                            dpToPx(context, 62).toFloat(),
                            posXl.toFloat(),
                            dpToPx(context, 75).toFloat(),
                            smallDegLine)
                    }
                }

                if (degrees % 30 == 0) {
                    drawDegreeText(c, posXl, degrees)
                }

                posXl -= offset
                degrees += 5
            }

            // lines to the right (decrease degrees)
            var posXr = posX + offset
            degrees = degStart - 5
            while (posXr <= width) {
                when {
                    degrees % 10 == 0 -> {
                        c.drawLine(posXr.toFloat(),
                            dpToPx(context, 50).toFloat(),
                            posXr.toFloat(),
                            dpToPx(context, 75).toFloat(),
                            bigDegLine)
                    }
                    degrees % 5 == 0 -> {
                        c.drawLine(posXr.toFloat(),
                            dpToPx(context, 62).toFloat(),
                            posXr.toFloat(),
                            dpToPx(context, 75).toFloat(),
                            smallDegLine)
                    }
                }

                if (degrees % 30 == 0) {
                    drawDegreeText(c, posXr, degrees)
                }

                posXr += offset
                degrees -= 5
            }
        }
    }

    private fun drawDegreeText(canvas: Canvas, posX: Int, degrees: Int) {
        canvas.drawText(deg2compass(degrees), posX.toFloat(), dpToPx(context, 35).toFloat(), numDegText)
    }

    private fun deg2compass(degrees: Int): String {
        return when (val normalized = ((degrees % 360) + 360) % 360) {
            0 -> "N"
            90 -> "E"
            180 -> "S"
            270 -> "W"
            else -> (normalized / 10).toString()
        }
    }

    private fun dpToPx(context: Context, value: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(), context.resources.displayMetrics).toInt()
    }

}
