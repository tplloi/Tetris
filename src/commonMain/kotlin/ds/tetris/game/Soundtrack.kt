/*
 * © 2017-2022 Deviant Studio
 */

package ds.tetris.game

import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.readSound
import com.soywiz.korio.file.std.resourcesVfs
import ds.tetris.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class Clip(vararg val res: String) {
    START("s_start"),
    GAME_OVER("s_game_over"),
    PAUSE("s_rotate"),
    MOVE("s_move1", "s_move2", "s_move3", "s_move4"),
    ROTATE("s_rotate"),
    WIPE("s_wipe1", "s_wipe2", "s_wipe3", "s_wipe4"),
    LEVEL_UP("s_level_up");

}


open class Soundtrack(scope: CoroutineScope) : CoroutineScope by scope {

    var enabled: Boolean = true
    private val sounds: MutableMap<String, Sound> = mutableMapOf()

    fun init() {
        launchMultiplatform {
            Clip.values().flatMap { it.res.toList() }.forEach { key ->
                sounds[key] = resourcesVfs["sound/$key.mp3"].readSound()
            }
        }
    }

    fun move() = play(Clip.MOVE)
    fun gameOver() = play(Clip.GAME_OVER)
    fun rotate() = play(Clip.ROTATE)
    fun pause() = play(Clip.ROTATE)
    fun wipe(lines: Int) = play(Clip.WIPE.res[lines - 1])
    fun levelUp() = play(Clip.LEVEL_UP)

    private fun play(clip: Clip) {
        val key = clip.res.random()
        play(key)
    }

    private fun play(key: String) {
        if (enabled) {
            launchMultiplatform {
                sounds[key]?.play() ?: log.v("no sound found: $key")
            }
        }

    }

    /**
     * unique on android
     */
    open fun CoroutineScope.launchMultiplatform(task: suspend () -> Unit) {
        launch {
            task()
        }
    }

}

