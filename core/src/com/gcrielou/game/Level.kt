package com.gcrielou.game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import utils.*
import kotlin.math.ceil
import kotlin.math.max

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
    var tileWoodenBridgeVertical = Tile(Sprite(10, 2))
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

    lateinit var levelLayer: List<ArrayList<String>>
    lateinit var elementsLayer: List<ArrayList<String>>

    var levelNumber = 0

    init {
        level0()
    }

    private fun level0() {
        var levelLayer1: ArrayList<ArrayList<String>>

        levelLayer1 = arrayListOf(
                arrayListOf(CTL_G, TOP_G, TOP_G, CTR_G),
                arrayListOf(LEF_G, MID_G, MID_G, RIG_G),
                arrayListOf(LEF_G, MID_G, MID_G, RIG_G),
                arrayListOf(LEF_G, MID_G, MID_G, RIG_G),
                arrayListOf(LEF_G, MID_G, MID_G, RIG_G),
                arrayListOf(LEF_G, MID_G, MID_G, RIG_G),
                arrayListOf(LEF_G, MID_G, MID_G, RIG_G),
                arrayListOf(CBL_G, BOT_G, BOT_G, CBR_G),
                arrayListOf(FLOA1, FLOA2, FLOA2, FLOA3)
        )

        elementsLayer = arrayListOf(
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, ROCK1, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, MUSH1, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, ROCK2, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY)
        )


        // reverse for easier manipulation with (x,y) = (0,0) in the bottom left of the level representation
        val shiftY = 5
        val shiftX = 10
        levelLayer = fusionLevelArray(ArrayList<ArrayList<String>>(), levelLayer1.reversed(), shiftX, shiftY)
        elementsLayer = fusionLevelArray(ArrayList<ArrayList<String>>(), elementsLayer.reversed(), shiftX, shiftY)
    }

    public fun level1() {
        levelNumber = 1
        var levelLayer1: ArrayList<ArrayList<String>>
        var elementsLayer1: ArrayList<ArrayList<String>>

        levelLayer1 = arrayListOf(
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, CTWAY, H_WAY, H_WAY, H_WAY, MID_G),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BRIV1, FLOA2, FLOA2, FLOA2),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BRIV1, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, BRIV1, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, V_WAY, EMPTY, EMPTY, EMPTY),
                arrayListOf(CTL_G, TOP_G, TOP_G, TOP_G, TOP_G, TOP_G, MID_G, TOP_G, TOP_G, CTR_G),
                arrayListOf(CBL_G, CBLFG, MID_G, MID_G, MID_G, MID_G, MID_G, MID_G, MID_G, RIG_G),
                arrayListOf(FLOA1, CBL_G, BOT_G, BOT_G, BOT_G, BOT_G, BOT_G, BOT_G, BOT_G, CBR_G),
                arrayListOf(EMPTY, FLOA1, FLOA2, FLOA2, FLOA2, FLOA2, FLOA2, FLOA2, FLOA2, FLOA3)
        )

        elementsLayer1 = arrayListOf(
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, MUSH1, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, ROCK1, EMPTY, EMPTY, MUSH2, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY),
                arrayListOf(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY)
        )


        // reverse for easier manipulation with (x,y) = (0,0) in the bottom left of the level representation
        levelLayer = fusionLevelArray(levelLayer, levelLayer1.reversed(), 0, 0)
        elementsLayer = fusionLevelArray(elementsLayer, elementsLayer1.reversed(), 0, 0)
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

    private fun getTileType(level: List<ArrayList<String>>, x: Int, y: Int): tileType? {

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

    private fun fusionLevelArray(levelLayer1: List<ArrayList<String>>, levelLayer2: List<ArrayList<String>>, x: Int = 0, y: Int = 0): ArrayList<ArrayList<String>> {

        // copy first layer to level
        var fusionLevel = ArrayList<ArrayList<String>>(200)

        val newSize = max(levelLayer1.size, levelLayer2.size + y)

        while (fusionLevel.size < newSize) {
            fusionLevel.add(ArrayList())
        }

        for ((ay, ayArray) in levelLayer1.withIndex()) {
            for ((ax, sprite) in ayArray.withIndex()) {
                fusionLevel.get(ay).add(ax, sprite)
            }
        }

        // copy second layer to level with (x,y) shift
        for ((ay, ayArray) in levelLayer2.withIndex()) {
            for ((ax, sprite) in ayArray.withIndex()) {
                for (i in ax..ax + x) {
                    fusionLevel.get(ay + y).add(EMPTY)
                }
                val yArray: ArrayList<String> = fusionLevel.get(ay + y)
                yArray[ax + x] = sprite
            }
        }

        return fusionLevel
    }
}
