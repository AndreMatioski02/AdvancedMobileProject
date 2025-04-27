package com.kotlin.advancedmobileproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.advancedmobileproject.screen.FirstScreen

class MainActivity : AppCompatActivity() {

    private var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Fonts.initializeFonts(this)

        game = Game(this).also { g ->
            setContentView(g.render)
        }

        game?.let { g ->
            g.actualScreen = FirstScreen(this, g)
        }
    }
}