package com.gcrielou.game

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.viewport.FitViewport
import utils.clearScreen
import utils.drawSprite
import utils.use
import kotlin.system.exitProcess

/**
 * Created by gilles on 03-Dec-17.
 */

class MyGame : GameBase() {

    lateinit var batch: SpriteBatch
    lateinit var img: Texture
    lateinit var camera: OrthographicCamera
    lateinit var viewport: FitViewport
    var character: Character = Character()
    lateinit var level: Level
    lateinit var renderer: ShapeRenderer

    override fun create() {
        batch = SpriteBatch()
        img = Texture("char_sprites.png")
        level = Level(Texture("env_sprites.png"))
        camera = OrthographicCamera()
        viewport = FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        Gdx.input.inputProcessor = this
    }

    override fun render() {
        clearScreen()
        batch.projectionMatrix = camera.combined
        batch.use { draw() }

        drawGrid()

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    private fun draw() {
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if (level.canMoveDown(character.positionX, character.positionY))
                character.currentState = "RUNNING"
                character.moveDown()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            character.currentState = "RUNNING"
            if (level.canMoveUp(character.positionX, character.positionY))
                character.moveUp()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            character.currentState = "RUNNING"
            if (level.canMoveRight(character.positionX, character.positionY))
                character.moveRight()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            character.currentState = "RUNNING_LEFT"
            if (level.canMoveLeft(character.positionX, character.positionY))
                character.moveLeft()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            character.currentState = "JUMP"
        }

        level.draw(batch)
        character.drawCharacter(batch, img)
    }

    private fun drawGrid() {
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.WHITE

        for (y in 0..Config.WORLD_HEIGHT.toInt()) {
            renderer.line(0f, y.toFloat(), Config.WORLD_WIDTH, y.toFloat())
        }

        for (x in 0..Config.WORLD_WIDTH.toInt()) {
            renderer.line(x.toFloat(), 0f, x.toFloat(), Config.WORLD_HEIGHT)
        }

        renderer.end()
    }

    companion object {

    }

    override fun keyDown(keycode: Int): Boolean {

        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        character.currentState = "IDLE"
        return false
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
        renderer.dispose()
    }


}
