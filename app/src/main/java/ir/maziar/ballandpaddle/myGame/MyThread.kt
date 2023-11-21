package ir.maziar.ballandpaddle.myGame

import android.view.SurfaceHolder

class MyThread(private val surfaceHolder: SurfaceHolder,private val  myGame: MyGame) : Thread() {

    var isRunning: Boolean = true

    override fun run() {
        super.run()
        while (isRunning){
            val canvas = surfaceHolder.lockCanvas()
            synchronized(surfaceHolder){
                myGame.drawing(canvas)
                myGame.update()
            }
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }
}