package ir.maziar.ballandpaddle.myGame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent

class MyPaddle(private val screenWidth  : Int,private val screenHeight : Int) {

    var rectWidth = screenWidth / 5f
    var rectHeight = screenHeight / 27f
    var rectX = (screenWidth / 2) - (rectWidth / 2)
    var rectY = screenHeight - (screenHeight / 6f)
    val bounds get() = RectF(rectX,rectY,rectX+rectWidth,rectY+rectHeight)

    private val rectPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    fun drawPaddle(canvas: Canvas){
        canvas.drawRect(bounds,rectPaint)
    }


    fun handleTouchEvent(event: MotionEvent) {
        when(event.action){
            MotionEvent.ACTION_MOVE -> {
                rectX = event.x - (rectWidth / 2)
                if (rectX < 0){
                    rectX = 0f
                }
                if (rectX + rectWidth > screenWidth){
                    rectX = screenWidth - rectWidth
                }
            }
        }
    }
}