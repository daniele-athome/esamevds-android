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
import android.view.View
import java.util.*

class VCompassView: View {

    // any random degree divisible by 30
    private val degStart = Random().nextInt(12) * 30

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
    }

    override fun onDraw(canvas: Canvas?) {
        // left+right: 5 degrees (10 lines, 5 big + 5 small)

        canvas?.let { c ->
            val startX = 10f;
            var i = 0
            while ((startX + degLineX(i)) < width) {
                val posX = startX + degLineX(i)
                if (i % 2 == 0) {
                    c.drawLine(posX, 200f, posX, 300f, bigDegLine)
                }
                else {
                    c.drawLine(posX, 250f, posX, 300f, smallDegLine)
                }
                if (i % 6 == 0) {
                    // FIXME degrees decrease going right
                    c.drawText(deg2compass(degStart - (i/2*10)), posX, 150f, numDegText)
                }
                i++
            }
        }
    }

    private fun degLineX(ord: Int): Float {
        return (ord * 60f)
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

    companion object {

        val numDegText = Paint().apply {
            color = Color.BLACK
            textSize = 150f
            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }

        val smallDegLine = Paint().apply {
            color = Color.BLACK
            // TODO what unit? Pixels?
            strokeWidth = 20f
        }

        val bigDegLine = Paint().apply {
            color = Color.BLACK
            // TODO what unit? Pixels?
            strokeWidth = 20f
        }

    }

}
