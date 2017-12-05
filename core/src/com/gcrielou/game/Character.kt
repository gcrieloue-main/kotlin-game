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

    private var lastAnimationDrawing: Float = 0f
    private var currentSprite = 1
    private var speed = 0.1

    var statesSprites: Map<String, Array<Sprite>> = mapOf(
            "RUNNING" to arrayOf(Sprite(9, 1), Sprite(9, 2), Sprite(9, 3)),
            "IDLE" to arrayOf(Sprite(7, 1), Sprite(7, 2), Sprite(7, 3), Sprite(7, 3)),
            "JUMP" to arrayOf(Sprite(12, 1), Sprite(12, 2))
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

        if (lastAnimationDrawing >= speed) {
            val nbSprites = sprites?.size ?: 0
            currentSprite = (currentSprite + 1) % nbSprites
            lastAnimationDrawing = 0f
        }

        batch.drawSprite(img, 200f, 50f,
                Config.SPRITE_SIZE, sprites!![currentSprite].x, sprites!![currentSprite].y)
    }
}