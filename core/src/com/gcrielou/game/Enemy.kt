package com.gcrielou.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import utils.drawSprite

/**
 * Created by gcrielou on 05/12/2017.
 */
class Enemy() {

    var currentState = "IDLE"
    private var lastState = "RUNNING"

    var positionX = 5f * Config.SPRITE_SIZE_WORLD_UNIT
    var positionY = 3f * Config.SPRITE_SIZE_WORLD_UNIT

    private var lastAnimationDrawing: Float = 0f
    private var currentSprite = 1
    private var animationSpeed = Config.ANIMATION_SPEED


    var statesSprites: Map<String, Array<Sprite>> = mapOf(
            "IDLE" to arrayOf(Sprite(0, 0), Sprite(0, 0), Sprite(1, 0))
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
                sprite?.x ?: 0,
                sprite?.y ?: 0,
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

    fun computeMoveLength() = Gdx.graphics.deltaTime * Config.ENEMY_SPEED


}