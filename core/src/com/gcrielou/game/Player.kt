package com.gcrielou.game

import com.badlogic.gdx.graphics.Texture

/**
 * Created by gilles on 09-Dec-17.
 */
class Player(texture:Texture) : Character(texture) {
    override var statesSprites: Map<String, Array<Sprite>> = mapOf(
            "RUNNING" to arrayOf(Sprite(1, 9), Sprite(2, 9), Sprite(3, 9)),
            "RUNNING_LEFT" to arrayOf(Sprite(1, 9, flipX = true), Sprite(2, 9, flipX = true), Sprite(3, 9, flipX = true)),
            "IDLE" to arrayOf(Sprite(1, 7), Sprite(2, 7), Sprite(3, 7), Sprite(3, 7)),
            "JUMP" to arrayOf(Sprite(12, 1), Sprite(12, 2)),
            "FIGHT" to arrayOf(Sprite(3, 1), Sprite(3, 2), Sprite(3, 3), Sprite(3, 4))
    )
}