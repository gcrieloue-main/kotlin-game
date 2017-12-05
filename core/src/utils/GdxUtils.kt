package utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

/**
 * Created by gcrielou on 05/12/2017.
 */
fun clearScreen() {
    Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
}