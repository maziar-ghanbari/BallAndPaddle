package ir.maziar.ballandpaddle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context, attrs: AttributeSet?) : SurfaceView(context, attrs), SurfaceHolder.Callback {
    private var thread: GameThread? = null
    private var paddle: Paddle? = null
    private var ball: Ball? = null
    private var bricks: MutableList<Brick> = mutableListOf()

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        setOnTouchListener { _, event ->
            paddle?.handleTouchEvent(event)
            true
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        paddle = Paddle(width, height)
        ball = Ball(width, height)
        bricks = createBricks()
        thread?.running = true
        thread?.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        thread?.running = false
        while (retry) {
            try {
                thread?.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE)
        paddle?.draw(canvas)
        ball?.draw(canvas)
        bricks.forEach { brick ->
            if (brick.visible) {
                brick.draw(canvas)
            }
        }
    }

    fun update() {
        paddle?.update()
        ball?.update()
        checkCollision()
        checkGameOver()
    }

    private fun checkCollision() {
        ball?.let { ball ->
            paddle?.let { paddle ->
                if (RectF.intersects(ball.bounds, paddle.bounds)) {
                    ball.reverseY()
                }
            }
            bricks.forEach { brick ->
                if (brick.visible && RectF.intersects(ball.bounds, brick.bounds)) {
                    brick.visible = false
                    ball.reverseY()
                }
            }
        }
    }

    private fun checkGameOver() {
        ball?.let { ball ->
            if (ball.y + ball.radius >= height) {
                thread?.running = false
            }
        }
    }

    private fun createBricks(): MutableList<Brick> {
        val bricks = mutableListOf<Brick>()
        val brickWidth = width / 8f
        val brickHeight = height / 10f
        for (row in 0 until 3) {
            for (col in 0 until 8) {
                val brick = Brick(col * brickWidth, row * brickHeight, brickWidth, brickHeight)
                bricks.add(brick)
            }
        }
        return bricks
    }
}