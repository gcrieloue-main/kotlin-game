package com.gcrielou.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.gcrielou.game.Game

/**
 * Created by gilles on 03-Dec-17.
 */

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.height = 720
    config.width = 1080
    LwjglApplication(Game(), config)
}
