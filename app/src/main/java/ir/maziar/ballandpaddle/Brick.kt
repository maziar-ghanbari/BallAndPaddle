package ir.maziar.ballandpaddle

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Brick(private val x: Float, private val y: Float, private val width: Float, private val height: Float) {
    var visible = true

    private val paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }

    val bounds: RectF
        get() = RectF(x, y, x + width, y + height)

    fun draw(canvas: Canvas) {
        canvas.drawRect(bounds, paint)
    }
}