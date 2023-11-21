package ir.maziar.ballandpaddle.myGame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class MyBlocks(
    left: Float,
    top: Float,
    right: Float,
    bottom: Float
) {
    var isVisible = true
    private val fillPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }
    private val strokePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.CYAN
        strokeWidth = 20f
    }

    val bounds = RectF(left, top, right, bottom)

    fun draw(canvas: Canvas): Boolean {
        if (isVisible) {
            canvas.drawRect(bounds, fillPaint)
            canvas.drawRect(bounds, strokePaint)
        }
        return isVisible
    }

    companion object {
        fun createBlocks(widthScreen: Int, heightScreen: Int): MutableList<MyBlocks> {
            val blockWidth = widthScreen / 2f
            val blockHeight = heightScreen / 23f
            val myBlocks: MutableList<MyBlocks> = mutableListOf()
            var x: Float
            var y: Float
            for (i: Int in 0 until 10) {
                y = i * blockHeight
                for (j: Int in 0 until 2) {
                    x = j * blockWidth
                    val myBlock = MyBlocks(x, y, x + blockWidth, y + blockHeight)
                    myBlocks.add(myBlock)
                }
            }
            return myBlocks
        }
    }


}