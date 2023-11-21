package ir.maziar.ballandpaddle

import android.graphics.Canvas
import android.view.SurfaceHolder

class GameThread(private val surfaceHolder: SurfaceHolder, private val gameView: GameView) : Thread() {
    var running = false

    override fun run() {
        while (running) {
            val canvas: Canvas? = surfaceHolder.lockCanvas()
            canvas?.let {
                synchronized(surfaceHolder) {
                    gameView.update()
                    gameView.draw(it)
                }
                surfaceHolder.unlockCanvasAndPost(it)
            }
        }
    }
}