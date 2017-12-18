package com.gcrielou.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import utils.drawSprite
import utils.toSpriteUnits
import utils.toWorldUnits

/**
 * Created by gcrielou on 05/12/2017.
 */
open class Character(var texture: Texture) {

    enum class Orientation {
        UP,
        LEFT,
        DOWN,
        RIGHT,
        NONE
    }

    var currentState = "IDLE"
    private var lastState = "RUNNING"

    var positionX = 2f * Config.SPRITE_SIZE_WORLD_UNIT
    var positionY = 3f * Config.SPRITE_SIZE_WORLD_UNIT

    private var lastAnimationDrawing: Float = 0f
    private var currentSprite = 1
    private var animationSpeed = Config.ANIMATION_SPEED

    var orientation: Orientation = Orientation.NONE

    open var health = 3
    var secondsSinceLastHealthLost = 0f

    open var statesSprites: Map<String, Array<Sprite>> = mapOf(
            "RUNNING" to arrayOf(Sprite(1, 9), Sprite(2, 9), Sprite(3, 9)),
            "IDLE" to arrayOf(Sprite(1, 7), Sprite(2, 7), Sprite(3, 7), Sprite(7, 3)),
            "JUMP" to arrayOf(Sprite(12, 1), Sprite(12, 2)),
            "FIGHT" to arrayOf(Sprite(1, 3), Sprite(2, 3), Sprite(3, 3), Sprite(4, 3))
    )

    fun drawCharacter(batch: Batch) {

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
            if (nbSprites > 0) {
                currentSprite = (currentSprite + 1) % nbSprites
                lastAnimationDrawing = 0f
            }
        }

        val sprite = sprites?.get(currentSprite)
        batch.drawSprite(texture, positionX, positionY,
                Config.SPRITE_SIZE,
                sprite?.x ?: 0,
                sprite?.y ?: 0,
                orientation == Orientation.LEFT)
    }

    fun moveLeft(distance: Float) {
        currentState = "RUNNING"
        positionX -= distance
        orientation = Orientation.LEFT
    }

    fun moveRight(distance: Float) {
        currentState = "RUNNING"
        println("$positionX+$distance")
        positionX += distance
        println("=>+$distance")
        orientation = Orientation.RIGHT
    }

    fun hold() {
        currentState = "IDLE"
        orientation = Orientation.NONE
    }

    fun moveUp(distance: Float) {
        positionY += distance
        orientation = Orientation.UP
    }

    fun moveDown(distance: Float) {
        positionY -= distance
        orientation = Orientation.DOWN
    }

    open fun computePlayerMoveLength() = Gdx.graphics.deltaTime * Config.PLAYER_SPEED

    fun getEnemyRecoil(playerX: Float, playerY: Float): Pair<Float, Float> {
        // we want the enemy to go back in the character opposite direction for distanceX distance of 1 sprite

        var moveX = 0.0
        var moveY = 0.0

        var distanceX = (positionX - playerX).toSpriteUnits()
        var distanceY = (positionY - playerY).toSpriteUnits()


        if (distanceY == 0f) {
            // same playerY axis
            moveX = 1.0
            moveY = 0.0
        } else if (distanceX == 0f) {
            // same playerX axis
            moveX = 0.0
            moveY = 1.0
        } else {
            // distanceEnemy is the distance between enemy and character
            var distanceEnemy = Math.sqrt((distanceX * distanceX + distanceY * distanceY).toDouble())

            // moveX is the distance the enemy go back on playerX axis
            moveX = distanceX / distanceEnemy
            // moveX is the distance the enemy go back on playerY axis
            moveY = Math.sqrt(1 - moveX * moveX)
        }
        println(moveX)

        return Pair(moveX.toFloat().toWorldUnits(), moveY.toFloat().toWorldUnits())
    }

    fun loseHealth() {
        health--
    }

    fun isAlive() = health > 0

    fun computeMoveLength() = Gdx.graphics.deltaTime * Config.ENEMY_SPEED
}