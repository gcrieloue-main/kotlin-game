package com.gcrielou.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import utils.clearScreen
import utils.drawSprite
import utils.use

/**
 * Created by gilles on 03-Dec-17.
 */

class Game : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var img: Texture

    lateinit var camera: OrthographicCamera

    lateinit var viewport: FitViewport

    override fun create() {
        batch = SpriteBatch()
        img = Texture("sprites01.png")
        camera = OrthographicCamera()
        viewport = FitViewport(1080f, 720f, camera)
    }

    override fun render() {
        clearScreen()
        batch.projectionMatrix = camera.combined
        batch.use { draw() }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    private var lastAnimationDrawing: Float = 0f
    private var currentSprite = 0

    companion object {
        const val SPRITE_SIZE = 16f
    }

    class Character() {

        class Sprite(var x: Int, var y: Int, var frameNumber: Int = 1) {}

        private var lastAnimationDrawing: Float = 0f
        private var currentSprite = 1
        private var speed = 0.1
        var currentState = "IDLE"
        private var lastState = "RUNNING"

        var statesSprites: Map<String, Array<Sprite>> = mapOf(
                "RUNNING" to arrayOf(Sprite(9, 1), Sprite(9, 2), Sprite(9, 3)),
                "IDLE" to arrayOf(Sprite(7, 1), Sprite(7, 2), Sprite(7, 3), Sprite(7, 3)),
                "JUMP" to arrayOf(Sprite(12, 1), Sprite(12, 2))
        )

        fun drawCharacter(batch: Batch, img: Texture) {

            if (currentState != lastState) {
                lastAnimationDrawing = 0f
                currentSprite = 0
                lastState = currentState
            }

            var sprites: Array<Sprite>? = statesSprites.get(currentState)

            val deltaTime = Gdx.graphics.deltaTime
            lastAnimationDrawing += deltaTime

            if (lastAnimationDrawing >= speed) {
                currentSprite = (currentSprite + 1) % sprites!!.size
                lastAnimationDrawing = 0f
            }

            batch.drawSprite(img, 200f, 50f,
                    SPRITE_SIZE, sprites!![currentSprite].x, sprites!![currentSprite].y)
        }
    }

    var character: Character = Character()

    private fun draw() {

        when {
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> character.currentState = "RUNNING"
            Gdx.input.isKeyPressed(Input.Keys.DOWN) -> character.currentState = "IDLE"
            Gdx.input.isKeyPressed(Input.Keys.UP) -> character.currentState = "JUMP"
        }

        character.drawCharacter(batch, img)
    }

    private fun showSpritesSamples(deltaTime: Float, speed: Double) {
        val deltaTime = Gdx.graphics.deltaTime
        val speed = 0.1

        if (lastAnimationDrawing >= speed) {
            lastAnimationDrawing = 0f
            val spriteStart = 1
            val spriteEnd = 10
            currentSprite = (currentSprite + spriteStart) % spriteEnd + 1
        } else lastAnimationDrawing += deltaTime


        var currentY = 100f
        var currentLine = 1
        for (i in 1..10) {
            batch.drawSprite(img, 200f, currentY,
                    SPRITE_SIZE, currentLine, currentSprite)

            currentY += SPRITE_SIZE * 2
            currentLine++
        }
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}
