package utils

import com.gcrielou.game.Config

/**
 * Created by gilles on 07-Dec-17.
 */

inline fun Float.ceil() = kotlin.math.ceil(this).toInt()

inline fun Float.floor() = kotlin.math.floor(this).toInt()

inline fun Float.toSpriteUnits() = this / Config.SPRITE_SIZE_WORLD_UNIT
inline fun Float.toWorldUnits() = this * Config.SPRITE_SIZE_WORLD_UNIT

fun distance(coord1: Pair<Float, Float>, coord2: Pair<Float, Float>): Float {
    var b = coord2.first - coord1.first
    var a = coord2.second - coord1.second
    return Math.sqrt((a * a + b * b).toDouble()).toFloat()
}