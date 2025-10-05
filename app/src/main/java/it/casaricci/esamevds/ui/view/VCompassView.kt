package it.casaricci.esamevds.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import it.casaricci.esamevds.Utils

class VCompassView: View {

    // any random degree divisible by 30
    //private val degStart = Random().nextInt(12) * 30
    // any random degree divisible by 5
    //private val degStart = Random().nextInt(72) * 5

    private lateinit var numDegText: Paint
    private lateinit var smallDegLine: Paint
    private lateinit var bigDegLine: Paint

    var degrees: Int = 0
        set(value) {
            if (value % 5 != 0) {
                throw IllegalArgumentException("Degrees must be divisible by 5!")
            }
            field = Utils.normalizeDegrees(value)
            invalidate()
        }

    constructor(context: Context): super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        numDegText = Paint().apply {
            color = Color.WHITE
            textSize = dpToPx(context, 35).toFloat()
            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            textAlign = Paint.Align.CENTER
        }

        smallDegLine = Paint().apply {
            color = Color.WHITE
            strokeWidth = dpToPx(context, 5).toFloat()
        }

        bigDegLine = Paint().apply {
            color = Color.WHITE
            strokeWidth = dpToPx(context, 5).toFloat()
        }
    }

    override fun onDraw(canvas: Canvas) {
        // left+right: 5 degrees (10 lines, 5 big + 5 small)

        canvas.drawColor(Color.BLACK)

        // start from center
        val posX = width / 2
        when {
            degrees % 10 == 0 -> {
                canvas.drawLine(posX.toFloat(), dpToPx(context, 50).toFloat(), posX.toFloat(), dpToPx(context, 75).toFloat(), bigDegLine)
            }

            degrees % 5 == 0 -> {
                canvas.drawLine(posX.toFloat(), dpToPx(context, 62).toFloat(), posX.toFloat(), dpToPx(context, 75).toFloat(), smallDegLine)
            }

            else -> {
                throw UnsupportedOperationException("Degrees must be divisible by either 5 or 10")
            }
        }

        if (degrees % 30 == 0) {
            drawDegreeText(canvas, posX, degrees)
        }

        val offset = dpToPx(context, 15)

        // lines to the left (increase degrees)
        var posXl = posX - offset
        var degreesPtr = degrees + 5
        while (posXl >= 0) {
            when {
                degreesPtr % 10 == 0 -> {
                    canvas.drawLine(posXl.toFloat(),
                        dpToPx(context, 50).toFloat(),
                        posXl.toFloat(),
                        dpToPx(context, 75).toFloat(),
                        bigDegLine)
                }

                degreesPtr % 5 == 0 -> {
                    canvas.drawLine(posXl.toFloat(),
                        dpToPx(context, 62).toFloat(),
                        posXl.toFloat(),
                        dpToPx(context, 75).toFloat(),
                        smallDegLine)
                }
            }

            if (degreesPtr % 30 == 0) {
                drawDegreeText(canvas, posXl, degreesPtr)
            }

            posXl -= offset
            degreesPtr += 5
        }

        // lines to the right (decrease degrees)
        var posXr = posX + offset
        degreesPtr = degrees - 5
        while (posXr <= width) {
            when {
                degreesPtr % 10 == 0 -> {
                    canvas.drawLine(posXr.toFloat(),
                        dpToPx(context, 50).toFloat(),
                        posXr.toFloat(),
                        dpToPx(context, 75).toFloat(),
                        bigDegLine)
                }

                degreesPtr % 5 == 0 -> {
                    canvas.drawLine(posXr.toFloat(),
                        dpToPx(context, 62).toFloat(),
                        posXr.toFloat(),
                        dpToPx(context, 75).toFloat(),
                        smallDegLine)
                }
            }

            if (degreesPtr % 30 == 0) {
                drawDegreeText(canvas, posXr, degreesPtr)
            }

            posXr += offset
            degreesPtr -= 5
        }
    }

    private fun drawDegreeText(canvas: Canvas, posX: Int, degrees: Int) {
        canvas.drawText(deg2compass(degrees), posX.toFloat(), dpToPx(context, 35).toFloat(), numDegText)
    }

    private fun deg2compass(degrees: Int): String {
        return when (val normalized = Utils.normalizeDegrees(degrees)) {
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
