package ir.maziar.ballandpaddle.myGame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class MyBall(private var width: Int, private var height: Int) {
    //x of center of circle
    private var circleX = width / 2f

    //y of center of circle
    private var circleY = height / 2f

    //شعاع دایره
    var radius = (width + height) / 100f

    //steps to move / speed
    private var sX = 30f
    private var sY = 30f

    private val circlePaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(circleX, circleY, radius, circlePaint)
    }

    fun update(paddle: MyPaddle, isVictory : Boolean) {
        //if ball pass the paddle
        if (circleY + radius + sY > paddle.bounds.bottom) {
            if (!isVictory) {
                //game Over and ball is in jail
                paddle.rectY = height.toFloat() / 2f
                paddle.rectX = 0f
                paddle.rectWidth = width.toFloat()
            }else{
                //victory and big red ball and black bg
                paddle.rectX = 0f
                paddle.rectY = 0f
                paddle.rectWidth = width.toFloat()
                paddle.rectHeight = height.toFloat()
            }
        }
        //reverse x and y by limit space
        if ((circleX + radius + sX > width) || (circleX - radius + sX < 0)) {
            reverseX()
        }
        if ((circleY + radius + sY > height) || (circleY - radius + sY < 0)) {
            reverseY()
        }
        //move
        circleX += sX
        circleY += sY
    }

    //live bounds
    val bounds
        get() = RectF(
            circleX - radius,
            circleY - radius,
            circleX + radius,
            circleY + radius
        )


    //reverse

    private fun reverseX() {
        sX = -sX
    }

    fun reverseY() {
        sY = -sY
    }
}