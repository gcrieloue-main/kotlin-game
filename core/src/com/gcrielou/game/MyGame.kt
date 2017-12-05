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

class MyGame : GameBase() {

    lateinit var batch: SpriteBatch
    lateinit var img: Texture
    lateinit var camera: OrthographicCamera
    lateinit var viewport: FitViewport
    var character: Character = Character()

    override fun create() {
        batch = SpriteBatch()
        img = Texture("char_sprites.png")
        camera = OrthographicCamera()
        viewport = FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera)

        Gdx.input.inputProcessor = this
    }

    override fun render() {
        clearScreen()
        batch.projectionMatrix = camera.combined
        batch.use { draw() }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    private fun draw() {
        character.drawCharacter(batch, img)
    }

    override fun keyDown(keycode: Int): Boolean {
        when {
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> character.currentState = "RUNNING"
            Gdx.input.isKeyPressed(Input.Keys.DOWN) -> character.currentState = "IDLE"
            Gdx.input.isKeyPressed(Input.Keys.UP) -> character.currentState = "JUMP"
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        character.currentState = "IDLE"
        return false
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}
