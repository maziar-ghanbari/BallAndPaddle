package ir.maziar.ballandpaddle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.maziar.ballandpaddle.myGame.MyGame

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MyGame(this,null))
    }
}