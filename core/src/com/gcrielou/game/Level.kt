package com.gcrielou.game;

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import utils.drawSprite

/**
 * Created by gcrielou on 05/12/2017.
 */
class Level(var texture: Texture) {

    enum class tileType {
        SOLID,
        VOID,
        EMPTY
    }

    class Tile(var sprite: Sprite, var type: tileType = tileType.EMPTY) // sprite + behavior

    companion object {
        private const val EMPTY = ""
        private const val TOP_G = "TOP_G"
        private const val BOT_G = "BOT_G"
        private const val CBL_G = "CBL_G"
        private const val CBLFG = "CBLFG"
        private const val CBR_G = "CBR_G"
        private const val CTL_G = "CTL_G"
        private const val CTR_G = "CTR_G"
        private const val MID_G = "MID_G"
        private const val RIG_G = "RIG_G"
        private const val LEF_G = "LEF_G"
        private const val ROCK1 = "ROCK1"
        private const val ROCK2 = "ROCK2"
        private const val MUSH1 = "MUSH1"
        private const val MUSH2 = "MUSH2"
        private const val H_WAY = "HWAY"
        private const val V_WAY = "VWAY"
        private const val CTWAY = "CTWAY"
        private const val FLOA1 = "FLOA1"
        private const val FLOA2 = "FLOA2"
        private const val FLOA3 = "FLOA3"
    }

    //<editor-fold desc="tiles">
    var tileCTL = Tile(Sprite(0, 0))
    var tileT = Tile(Sprite(1, 0))
    var tileCTR = Tile(Sprite(5, 0))

    var tileM = Tile(Sprite(4, 2))
    var tileL = Tile(Sprite(0, 1))
    var tileR = Tile(Sprite(5, 1))

    var tileCBL = Tile(Sprite(0, 4))
    var tileCBLFG = Tile(Sprite(3, 1))
    var tileB = Tile(Sprite(1, 4))
    var tileCBR = Tile(Sprite(5, 4))
    var tileHWAY = Tile(Sprite(7, 0))
    var tileVWAY = Tile(Sprite(6, 1))
    var tileCTWAY = Tile(Sprite(6, 0))

    var tileFLOA1 = Tile(Sprite(14, 4), tileType.SOLID)
    var tileFLOA2 = Tile(Sprite(15, 4), tileType.SOLID)
    var tileFLOA3 = Tile(Sprite(16, 4), tileType.SOLID)

    var tileRock = Tile(Sprite(8, 7), tileType.SOLID)
    var tileRock2 = Tile(Sprite(9, 7), tileType.SOLID)
    var tileMushroom = Tile(Sprite(10, 6))
    var tileMushroom2 = Tile(Sprite(10, 7))

    var tileSet: Map<String, Tile> = mapOf(
            TOP_G to tileT,
            BOT_G to tileB,
            CBL_G to tileCBL,
            CBLFG to tileCBLFG,
            CBR_G to tileCBR,
            CTL_G to tileCTL,
            CTR_G to tileCTR,
            MID_G to tileM,
            RIG_G to tileR,
            LEF_G to tileL,
            H_WAY to tileHWAY,
            V_WAY to tileVWAY,
            CTWAY to tileCTWAY,
            FLOA1 to tileFLOA1,
            FLOA2 to tileFLOA2,
            FLOA3 to tileFLOA3,
            ROCK1 to tileRock,
            ROCK2 to tileRock2,
            MUSH1 to tileMushroom,
            MUSH2 to tileMushroom2
    )
    //</editor-fold>

    var levelLayer: Array<Array<String>>
    var elementsLayer: Array<Array<String>>

    init {
        levelLayer = arrayOf(
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, CTL_G, TOP_G, CTR_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, CTWAY, H_WAY, H_WAY, H_WAY, MID_G, MID_G, RIG_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, V_WAY, FLOA1, FLOA2, FLOA3, CBL_G, BOT_G, CBR_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, V_WAY, EMPTY, EMPTY, EMPTY, FLOA1, FLOA2, FLOA3),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, V_WAY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, V_WAY, EMPTY, EMPTY, EMPTY),
                arrayOf(CTL_G, TOP_G, TOP_G, TOP_G, TOP_G, TOP_G, MID_G, TOP_G, TOP_G, CTR_G),
                arrayOf(CBL_G, CBLFG, MID_G, MID_G, MID_G, MID_G, MID_G, MID_G, MID_G, RIG_G),
                arrayOf(EMPTY, CBL_G, BOT_G, BOT_G, BOT_G, BOT_G, BOT_G, BOT_G, BOT_G, CBR_G)
        )

        elementsLayer = arrayOf(
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, ROCK2, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, MUSH1, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, ROCK1, EMPTY, EMPTY, MUSH2, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)
        )

        // reverse for easier manipulation with (x,y) = (0,0) in the bottom left of the level representation
        levelLayer = levelLayer.reversedArray()
        elementsLayer = elementsLayer.reversedArray()
    }

    fun canMoveUp(x: Float, y: Float): Boolean {
        var isLastTile = false
        if (levelLayer.size - 1 == y.toInt()) isLastTile = true
        if (!isLastTile) {
            // get the type of the top tile
            isLastTile = (getTileType(x.toInt(), y.toInt() + 1) != tileType.EMPTY)

        }
        val remainingSpaceInTile = y.toInt() + 1 - y
        return remainingSpaceInTile > 0.1 || !isLastTile
    }


    fun canMoveRight(x: Float, y: Float): Boolean {
        if (levelLayer[y.toInt()].size == x.toInt() + 1) return false
        // get the type of the right tile
        return (getTileType((x + 1).toInt(), y.toInt()) == tileType.EMPTY)
    }

    fun canMoveLeft(x: Float, y: Float): Boolean {
        var isLastTile = false
        if (x.toInt() == 0 || levelLayer[y.toInt()].size == x.toInt() - 1) isLastTile = true
        if (!isLastTile) {
            // get the type of the left tile
            isLastTile = (getTileType((x - 1).toInt(), y.toInt()) != tileType.EMPTY)
        }
        val remainingSpaceInTile = x - x.toInt()
        return remainingSpaceInTile > 0.1 || !isLastTile
    }

    fun canMoveDown(x: Float, y: Float): Boolean {
        var isLastTile = false
        if (y.toInt() == 0) isLastTile = true
        if (!isLastTile) {
            // get the type of the bottom tile
            isLastTile = (getTileType(x.toInt(), y.toInt() - 1) != tileType.EMPTY)
        }
        val remainingSpaceInTile = y - y.toInt()
        println("$remainingSpaceInTile $isLastTile ${remainingSpaceInTile > 0.2} ${!isLastTile}")
        return remainingSpaceInTile > 0.2 || !isLastTile
    }

    private fun getTileType(x: Int, y: Int): tileType? {
        if (y < elementsLayer.size) {
            val row = elementsLayer.get(y)
            if (x < row.size) {
                val tileElementsStringRepresentation = row.get(x)
                if (tileElementsStringRepresentation.isNotEmpty()) {
                    val tileElements: Tile? = tileSet.get(tileElementsStringRepresentation)
                    val type = tileElements?.type
                    println("element type ($x, $y) : ${tileElements?.type}")
                    if (type != tileType.EMPTY)
                        return type
                }
            }
        }

        val row = levelLayer.get(y)
        if (x < row.size) {
            val tileLevelStringRepresentation = row.get(x)
            val tileLevel: Tile? = tileSet.get(tileLevelStringRepresentation)
            println("level type ($x, $y) : ${tileLevel?.type}")
            return tileLevel?.type
        }

        return null
    }

    fun draw(batch: Batch) {
        for ((y, row) in levelLayer.withIndex()) {
            for ((x, col) in row.withIndex()) {
                if (col.isNotEmpty()) {
                    val spriteX = tileSet.get(col)?.sprite?.x ?: 0
                    val spriteY = tileSet.get(col)?.sprite?.y ?: 0
                    batch.drawSprite(texture,
                            x, y,
                            Config.SPRITE_SIZE,
                            spriteX, spriteY)
                }
            }
        }

        for ((y, row) in elementsLayer.withIndex()) {
            for ((x, col) in row.withIndex()) {
                if (col.isNotEmpty()) {
                    val spriteX = tileSet.get(col)?.sprite?.x ?: 0
                    val spriteY = tileSet.get(col)?.sprite?.y ?: 0
                    batch.drawSprite(texture,
                            x, y,
                            Config.SPRITE_SIZE,
                            spriteX, spriteY)
                }
            }
        }
    }
}
