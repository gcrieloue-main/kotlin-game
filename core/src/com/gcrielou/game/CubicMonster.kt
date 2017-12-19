package com.gcrielou.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import utils.drawSprite

/**
 * Created by gcrielou on 05/12/2017.
 */
open class CubicMonster(texture: Texture, x: Int, y: Int) : Character(texture) {


    init {
        positionX = x * Config.SPRITE_SIZE_WORLD_UNIT
        positionY = y * Config.SPRITE_SIZE_WORLD_UNIT
    }

    override var statesSprites: Map<String, Array<Sprite>> = mapOf(
            "IDLE" to arrayOf(Sprite(0, 0), Sprite(1, 0), Sprite(2, 0)),
            "RUNNING" to arrayOf(Sprite(0, 1), Sprite(1, 1),
                    Sprite(2, 1)),
            "RUNNING_LEFT" to arrayOf(Sprite(0, 1, 1, true), Sprite(1, 1, 1, true),
                    Sprite(2, 1, 1, true)),
            "DEAD" to arrayOf(Sprite(0, 6, 1), Sprite(1, 6, 1), Sprite(2, 6, 1), Sprite(3, 6, 1), Sprite(4, 6, 1))
    )

    override var health: Int
        get() = super.health
        set(value) {
            super.health = value
            if (health == 0) {
                println("dead")
                this.currentState = "DEAD"
            }
        }

    override var currentState: String
        get() {
            return super.currentState
        }
        set(value) {
            if (!endStates.contains(currentState)) {
                super.currentState = value
            }
        }
}