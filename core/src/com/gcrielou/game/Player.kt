package com.gcrielou.game

import com.badlogic.gdx.graphics.Texture
import utils.toWorldUnits

/**
 * Created by gilles on 09-Dec-17.
 */
class Player(texture: Texture) : Character(texture) {
    override var statesSprites: Map<String, Array<Sprite>> = mapOf(
            "RUNNING" to arrayOf(Sprite(1, 9), Sprite(2, 9), Sprite(3, 9)),
            "RUNNING_LEFT" to arrayOf(Sprite(1, 9, flipX = true), Sprite(2, 9, flipX = true), Sprite(3, 9, flipX = true)),
            "IDLE" to arrayOf(Sprite(1, 7), Sprite(2, 7), Sprite(3, 7), Sprite(3, 7)),
            "UP" to arrayOf(Sprite(1, 0), Sprite(2, 0), Sprite(3, 0), Sprite(7, 0)),
            "JUMP" to arrayOf(Sprite(12, 1), Sprite(12, 2)),
            "FIGHT" to arrayOf(Sprite(3, 3))
    )

    fun isHurtingEnemy(enemy: Character): Boolean =
            when {
                orientation == Character.Orientation.RIGHT || orientation == Character.Orientation.UP -> {
                    (enemy.positionX >= positionX && enemy.positionX <= positionX + 2f.toWorldUnits()
                            && enemy.positionY + 1f.toWorldUnits() >= positionY && enemy.positionY <= positionY + 2f.toWorldUnits())
                }
                orientation == Character.Orientation.DOWN -> {
                    (enemy.positionX <= positionX + 1f.toWorldUnits() && enemy.positionX >= positionX - 1f.toWorldUnits()
                            && enemy.positionY <= positionY + 1f.toWorldUnits() && enemy.positionY + 1f.toWorldUnits() >= positionY - 1f.toWorldUnits())
                }
                orientation == Character.Orientation.LEFT -> {
                    (enemy.positionX > positionX - 2f.toWorldUnits() && enemy.positionX <= positionX + 1f.toWorldUnits()
                            && enemy.positionY + 1f.toWorldUnits() >= positionY && enemy.positionY <= positionY + 2f.toWorldUnits())
                }
                else -> false
            }
}