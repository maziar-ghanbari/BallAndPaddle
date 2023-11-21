package ir.maziar.ballandpaddle

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Ball(private val screenWidth: Int, private val screenHeight: Int) {
    val radius = (screenWidth + screenHeight) / 100f
    private val speedX = screenWidth / 100f
    private val speedY = screenHeight / 100f
    private var x = screenWidth / 2f
    var y = screenHeight / 2f
    private var dx = speedX
    private var dy = speedY

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    val bounds: RectF
        get() = RectF(x - radius, y - radius, x + radius, y + radius)

    fun draw(canvas: Canvas) {
        canvas.drawCircle(x, y, radius, paint)
    }

    fun update() {
        x += dx
        y += dy
        if (x - radius < 0 || x + radius > screenWidth) {
            dx = -dx
        }
        if (y - radius < 0 || y + radius > screenHeight) {
            dy = -dy
        }
    }

    fun reverseY() {
        dy = -dy
    }
}