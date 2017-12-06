package com.gcrielou.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch
import utils.drawSprite

/**
 * Created by gcrielou on 05/12/2017.
 */
public class Level(var texture: Texture) {


    enum class tileType {
        SOLID,
        VOID,
        EMPTY
    }

    class Tile(var sprite: Sprite, var type: tileType = tileType.EMPTY) // sprite + behavior


    var tileCTL = Tile(Sprite(0, 0))
    var tileT = Tile(Sprite(0, 1))
    var tileCTR = Tile(Sprite(0, 5))

    var tileM = Tile(Sprite(1, 4))
    var tileL = Tile(Sprite(1, 0))
    var tileR = Tile(Sprite(1, 5))

    var tileCBR = Tile(Sprite(4, 5))
    var tileB = Tile(Sprite(4, 4))
    var tileCBL = Tile(Sprite(4, 0))

    var tileRock = Tile(Sprite(7, 8), tileType.SOLID)

    var tileSet: Map<String, Tile> = mapOf("T" to tileT,
            "B" to tileB,
            "CBL" to tileCBL,
            "CBR" to tileCBR,
            "CTL" to tileCTL,
            "CTR" to tileCTR,
            "M" to tileM,
            "R" to tileR,
            "L" to tileL,
            "ROC" to tileRock)

    var level: Array<Array<String>> = arrayOf(
            arrayOf("CTL", "T", "T", "T", "T", "T", "T", "T", "T", "CTR"),
            arrayOf("L", "M", "M", "M", "M", "M", "M", "M", "M", "R"),
            arrayOf("CBL", "B", "B", "B", "B", "B", "B", "B", "B", "CBR")

    )

    var elements: Array<Array<String>> = arrayOf(
            arrayOf("", "", "", "", "", "", "", "", "", ""),
            arrayOf("", "", "", "", "", "ROC", "", "", "", ""),
            arrayOf("", "", "", "", "", "", "", "", "", "")
    )

    fun canMoveUp(x: Float, y: Float): Boolean {
        var isLastTile = false
        if (level.size - 1 == y.toInt()) isLastTile = true
        if (!isLastTile) {
            println(level.size - 1 - y.toInt() - 1)
            // get the type of the top tile
            val line = level.get(level.size - 1 - y.toInt() - 1)
            val tileStringRepresentation = line.get((x).toInt())
            val tile: Tile? = tileSet.get(tileStringRepresentation)
            println(tile?.type)
            isLastTile = (tile?.type != tileType.EMPTY)

        }
        val remainingSpaceInTile = y.toInt() + 1 - y
        println("$remainingSpaceInTile $isLastTile")
        return remainingSpaceInTile > 0.2 || !isLastTile
    }

    fun canMoveRight(x: Float, y: Float): Boolean {
        if (level[y.toInt()].size == x.toInt() + 1) return false
        // get the type of the right tile
        val line = level.get(y.toInt())
        val tileStringRepresentation = line.get((x + 1).toInt())
        val tile: Tile? = tileSet.get(tileStringRepresentation)
        return (tile?.type == tileType.EMPTY)
    }

    fun canMoveLeft(x: Float, y: Float): Boolean {
        var isLastTile = false
        if (x.toInt() == 0 || level[y.toInt()].size == x.toInt() - 1) isLastTile = true
        if (!isLastTile) {
            // get the type of the left tile
            val line = level.get(y.toInt())
            val tileStringRepresentation = line.get((x - 1).toInt())
            val tile: Tile? = tileSet.get(tileStringRepresentation)
            println(tile?.type)
            isLastTile = (tile?.type != tileType.EMPTY)
        }
        val remainingSpaceInTile = x - x.toInt()
        return remainingSpaceInTile > 0.2 || !isLastTile
    }

    fun canMoveDown(x: Float, y: Float): Boolean {
        var isLastTile = false
        if (y.toInt() == 0) isLastTile = true
        if (!isLastTile) {
            // get the type of the bottom tile
            val line = level.get(y.toInt() - 1)
            val tileStringRepresentation = line.get((x).toInt())
            val tile: Tile? = tileSet.get(tileStringRepresentation)
            println(tile?.type)
            isLastTile = (tile?.type != tileType.EMPTY)
        }
        val remainingSpaceInTile = y - y.toInt()
        return remainingSpaceInTile > 0.2 || !isLastTile
    }

    fun draw(batch: Batch) {
        for ((rowIndex, row) in level.reversed().withIndex()) {
            for ((colIndex, col) in row.withIndex()) {
                if (col.isNotEmpty()) {
                    val x = tileSet.get(col)?.sprite?.y ?: 0
                    val y = tileSet.get(col)?.sprite?.x ?: 0
                    batch.drawSprite(texture,
                            colIndex, rowIndex,
                            Config.SPRITE_SIZE,
                            x, y)
                }
            }
        }

        for ((rowIndex, row) in elements.reversed().withIndex()) {
            for ((colIndex, col) in row.withIndex()) {
                if (col.isNotEmpty()) {
                    val x = tileSet.get(col)?.sprite?.y ?: 0
                    val y = tileSet.get(col)?.sprite?.x ?: 0
                    batch.drawSprite(texture,
                            colIndex, rowIndex,
                            Config.SPRITE_SIZE,
                            x, y)
                }
            }
        }
    }
}
