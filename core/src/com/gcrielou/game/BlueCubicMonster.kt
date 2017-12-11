package com.gcrielou.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import utils.drawSprite

/**
 * Created by gcrielou on 05/12/2017.
 */
class BlueCubicMonster(texture: Texture, x: Int, y: Int) : CubicMonster(texture, x, y) {


    init {
        positionX = x * Config.SPRITE_SIZE_WORLD_UNIT
        positionY = y * Config.SPRITE_SIZE_WORLD_UNIT
    }

    override var statesSprites: Map<String, Array<Sprite>> = mapOf(
            "IDLE" to arrayOf(Sprite(0, 3), Sprite(1, 3), Sprite(2, 3)),
            "RUNNING" to arrayOf(Sprite(0, 4), Sprite(1, 4),
                    Sprite(2, 1)),
            "RUNNING_LEFT" to arrayOf(Sprite(0, 4, 1, true), Sprite(1, 4, 1, true),
                    Sprite(2, 4, 1, true))
    )

    override var health: Int
        get() = super.health
        set(value) {
            super.health = value
            println(value)
        }

}