package utils

import com.gcrielou.game.Config

/**
 * Created by gilles on 07-Dec-17.
 */

inline fun Float.ceil() = kotlin.math.ceil(this).toInt()

inline fun Float.floor() = kotlin.math.floor(this).toInt()

inline fun Float.toSpriteUnits() = this / Config.SPRITE_SIZE_WORLD_UNIT