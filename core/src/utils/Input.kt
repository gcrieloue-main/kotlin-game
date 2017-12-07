package utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

/**
 * Created by gilles on 07-Dec-17.
 */

inline fun Int.isKeyPressed(): Boolean {
    return Gdx.input.isKeyPressed(this)
}