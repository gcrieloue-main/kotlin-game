package com.gcrielou.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import utils.drawSprite

/**
 * Created by gcrielou on 05/12/2017.
 */
class Character() {

    var currentState = "IDLE"
    private var lastState = "RUNNING"

    var positionX = 2f * Config.SPRITE_SIZE_WORLD_UNIT
    var positionY = 3f * Config.SPRITE_SIZE_WORLD_UNIT

    private var lastAnimationDrawing: Float = 0f
    private var currentSprite = 1
    private var animationSpeed = Config.ANIMATION_SPEED

    private var health = 5
    var secondsSinceLastHealthLost = 0f

    var statesSprites: Map<String, Array<Sprite>> = mapOf(
            "RUNNING" to arrayOf(Sprite(9, 1), Sprite(9, 2), Sprite(9, 3)),
            "RUNNING_LEFT" to arrayOf(Sprite(9, 1, flipX = true), Sprite(9, 2, flipX = true), Sprite(9, 3, flipX = true)),
            "IDLE" to arrayOf(Sprite(7, 1), Sprite(7, 2), Sprite(7, 3), Sprite(7, 3)),
            "JUMP" to arrayOf(Sprite(12, 1), Sprite(12, 2)),
            "FIGHT" to arrayOf(Sprite(3, 1), Sprite(3, 2), Sprite(3, 3), Sprite(3, 4))
    )

    fun drawCharacter(batch: Batch, img: Texture) {

        // change state, reset sprite animation
        if (currentState != lastState) {
            lastAnimationDrawing = 0f
            currentSprite = 0
            lastState = currentState
        }

        var sprites: Array<Sprite>? = statesSprites.get(currentState)

        val deltaTime = Gdx.graphics.deltaTime
        lastAnimationDrawing += deltaTime

        if (lastAnimationDrawing >= animationSpeed) {
            val nbSprites = sprites?.size ?: 0
            currentSprite = (currentSprite + 1) % nbSprites
            lastAnimationDrawing = 0f
        }

        val sprite = sprites?.get(currentSprite)
        batch.drawSprite(img, positionX, positionY,
                Config.SPRITE_SIZE,
                sprite?.y ?: 0,
                sprite?.x ?: 0,
                flipX = sprite?.flipX ?: false)
    }

    fun moveLeft(distance: Float) {
        positionX -= distance
    }

    fun moveRight(distance: Float) {
        positionX += distance
    }

    fun moveUp(distance: Float) {
        positionY += distance
    }

    fun moveDown(distance: Float) {
        positionY -= distance
    }

    fun computePlayerMoveLength() = Gdx.graphics.deltaTime * Config.PLAYER_SPEED


    fun loseHealth() {
        secondsSinceLastHealthLost += Gdx.graphics.deltaTime
        if (secondsSinceLastHealthLost >= 1) {
            health--
            secondsSinceLastHealthLost = 0f
        }
    }

    fun isAlive() = health > 0
}