package com.gcrielou.game

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.viewport.FitViewport
import utils.clearScreen
import utils.drawSprite
import utils.use

/**
 * Created by gilles on 03-Dec-17.
 */

open class GameBase : ApplicationListener, InputProcessor {
    override fun resize(width: Int, height: Int) = Unit
    override fun create() = Unit
    override fun render() = Unit
    override fun dispose() = Unit
    override fun pause() = Unit
    override fun resume() = Unit

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
    override fun mouseMoved(screenX: Int, screenY: Int) = false
    override fun keyTyped(character: Char): Boolean = false
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = false
    override fun scrolled(amount: Int): Boolean = false
    override fun keyUp(keycode: Int): Boolean = false
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = false
    override fun keyDown(keycode: Int): Boolean = false
}
