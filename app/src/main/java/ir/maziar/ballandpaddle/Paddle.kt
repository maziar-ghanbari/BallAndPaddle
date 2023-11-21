package ir.maziar.ballandpaddle

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent

class Paddle(private val screenWidth: Int, private val screenHeight: Int) {
    private val width = screenWidth / 5f
    private val height = screenHeight / 20f
    private val speed = 10f
    private var x = screenWidth / 2f - width / 2f
    private var y = screenHeight - height * 2

    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    val bounds: RectF
        get() = RectF(x, y, x + width, y + height)

    fun draw(canvas: Canvas) {
        canvas.drawRect(bounds, paint)
    }

    fun handleTouchEvent(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                x = event.x - width / 2
                if (x < 0) {
                    x = 0f
                }
                if (x + width > screenWidth) {
                    x = screenWidth - width
                }
            }
        }
    }

    fun update() {
        // No update needed for the paddle
    }
}