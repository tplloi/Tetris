package ds.tetris.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ds.tetris.game.Game
import ds.tetris.ui.TetrisGame
import ds.tetris.ui.TetrisTheme
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val game: Game by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        setContent {
            TetrisTheme {
                TetrisGame(game)
            }
        }
    }
}
