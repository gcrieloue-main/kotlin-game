package com.gcrielou.game;

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import utils.*
import kotlin.math.ceil

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
        private const val BRIV1 = "BRIV1"
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
    var tileWoodenBridgeVertical= Tile(Sprite(10, 2))
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
            MUSH2 to tileMushroom2,
            BRIV1 to tileWoodenBridgeVertical
    )
    //</editor-fold>

    var levelLayer: Array<Array<String>>
    var elementsLayer: Array<Array<String>>

    init {
        levelLayer = arrayOf(
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, CTL_G, TOP_G, CTR_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, LEF_G, MID_G, RIG_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, LEF_G, MID_G, RIG_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, LEF_G, MID_G, RIG_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, LEF_G, MID_G, RIG_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, LEF_G, MID_G, RIG_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, CTWAY, H_WAY, H_WAY, H_WAY, MID_G, MID_G, RIG_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BRIV1, FLOA2, FLOA2, FLOA2, CBL_G, BOT_G, CBR_G),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BRIV1, EMPTY, EMPTY, EMPTY, FLOA1, FLOA2, FLOA3),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BRIV1, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, V_WAY, EMPTY, EMPTY, EMPTY),
                arrayOf(CTL_G, TOP_G, TOP_G, TOP_G, TOP_G, TOP_G, MID_G, TOP_G, TOP_G, CTR_G),
                arrayOf(CBL_G, CBLFG, MID_G, MID_G, MID_G, MID_G, MID_G, MID_G, MID_G, RIG_G),
                arrayOf(FLOA1, CBL_G, BOT_G, BOT_G, BOT_G, BOT_G, BOT_G, BOT_G, BOT_G, CBR_G),
                arrayOf(EMPTY, FLOA1, FLOA2, FLOA2, FLOA2, FLOA2, FLOA2, FLOA2, FLOA2, FLOA3)
        )

        elementsLayer = arrayOf(
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, ROCK1, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, MUSH1, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, ROCK2, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, MUSH1, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, ROCK1, EMPTY, EMPTY, MUSH2, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)
        )

        // reverse for easier manipulation with (x,y) = (0,0) in the bottom left of the level representation
        levelLayer = levelLayer.reversedArray()
        elementsLayer = elementsLayer.reversedArray()
    }


    fun canMoveRight(x: Float, y: Float, distance: Float): Boolean = (getTileType(x + Config.SPRITE_SIZE_WORLD_UNIT + distance, y) == tileType.EMPTY)

    fun canMoveLeft(x: Float, y: Float, distance: Float): Boolean = (getTileType(x - distance, y) == tileType.EMPTY)

    fun canMoveUp(x: Float, y: Float, distance: Float): Boolean = (getTileType(x + Config.SPRITE_SIZE_WORLD_UNIT / 2, y + distance) == tileType.EMPTY)

    fun canMoveDown(x: Float, y: Float, distance: Float): Boolean = (getTileType(x + Config.SPRITE_SIZE_WORLD_UNIT / 2, y - distance) == tileType.EMPTY)

    private fun getTileType(x: Float, y: Float): tileType? {

        var tileCoordX = (x.toSpriteUnits()).floor()
        var tileCoordY = (y.toSpriteUnits()).floor()

        val elementTileType = getTileType(elementsLayer, tileCoordX, tileCoordY)
        if (elementTileType != null && elementTileType != tileType.EMPTY) {
            return elementTileType
        }

        val levelTileType = getTileType(levelLayer, tileCoordX, tileCoordY)

        return levelTileType
    }

    private fun getTileType(level: Array<Array<String>>, x: Int, y: Int): tileType? {

        if (y >= 0 && y < level.size) {
            val row = level.get(y)
            if (x >= 0 && x < row.size) {
                val tileElementsStringRepresentation = row.get(x)
                if (tileElementsStringRepresentation.isNotEmpty()) {
                    val tileElements: Tile? = tileSet.get(tileElementsStringRepresentation)
                    return tileElements?.type
                }
            }
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
