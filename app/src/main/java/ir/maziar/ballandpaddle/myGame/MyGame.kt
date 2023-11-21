package ir.maziar.ballandpaddle.myGame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

@SuppressLint("ClickableViewAccessibility")
class MyGame(context: Context, attrs: AttributeSet?) : SurfaceView(context, attrs),
    SurfaceHolder.Callback {

    private val ball: MyBall by lazy { MyBall(width, height) }
    private val blocks: MutableList<MyBlocks> by lazy { MyBlocks.createBlocks(width, height) }
    private val thread: MyThread by lazy { MyThread(holder, this) }
    private val paddle: MyPaddle by lazy { MyPaddle(width, height) }

    init {
        holder.addCallback(this)

        setOnTouchListener { _, event ->
            this.paddle.handleTouchEvent(event)
            return@setOnTouchListener true
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread.isRunning = true
        thread.start()
        /*
            val canvas = holder.lockCanvas()
            //background
            canvas.drawColor(Color.WHITE)
            //rectangle
            val rectWidth = width / 5f
            val rectHeight = height / 18f
            val rectX = (width / 2) - (rectWidth / 2)
            val rectY = height - (height / 6f)
            val paddle = RectF(rectX,rectY,rectX+rectWidth,rectY+rectHeight)
            val rectPaint = Paint().apply {
                color = Color.BLACK
                style = Paint.Style.FILL
            }
            canvas.drawRect(paddle,rectPaint)
            //circle
            val circleX = width / 2f
            val circleY = height / 2f
            val radius = (width + height) / 100f
            val circlePaint = Paint().apply {
                color = Color.RED
                style = Paint.Style.FILL
            }
            canvas.drawCircle(circleX,circleY,radius,circlePaint)
            //matrix of blocks
            val blockWidth = width / 7f
            val blockHeight = height / 16f
            var blockX: Float
            var blockY: Float
            val blockPaint = Paint()
            for (i: Int in 0 until 7){
                blockY = i * blockHeight
                for (j : Int in 0 until 7){
                    blockX = j * blockWidth
                    blockPaint.apply {
                        style = Paint.Style.FILL
                        color = Color.BLUE
                    }
                    val block = RectF(blockX, blockY,blockX+blockWidth,blockY+blockHeight)
                    canvas.drawRect(block,blockPaint)
                    blockPaint.apply {
                        style = Paint.Style.STROKE
                        color = Color.CYAN
                        strokeWidth = 20f
                    }
                    canvas.drawRect(block,blockPaint)
                }
            }
            holder.unlockCanvasAndPost(canvas)
            */
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        thread.isRunning = false
        while (retry) {
            try {
                thread.join()
                retry = false
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun drawing(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        paddle.drawPaddle(canvas)
        ball.draw(canvas)
        val removed = mutableListOf<MyBlocks>()
        blocks.forEach {
            if (!it.draw(canvas)) {
                removed.add(it)
            }
        }
        blocks.removeAll(removed)
        removed.clear()
        if (blocks.isEmpty() && ball.radius < height / 2f){
            ball.radius += 3f
        }else if(blocks.isEmpty() && ball.radius >= height / 2f){
            thread.isRunning = false
        }
    }


    fun update() {
        ball.update(paddle,blocks.isEmpty())
        checkCollision()
    }

    private fun checkCollision() {
        if(RectF.intersects(paddle.bounds, ball.bounds)){
            if (blocks.isNotEmpty()) ball.reverseY()
        }
        blocks.forEach {
            if (RectF.intersects(it.bounds, ball.bounds)) {
                ball.reverseY()
                ball.radius--
                it.isVisible = false
            }
        }
    }

}