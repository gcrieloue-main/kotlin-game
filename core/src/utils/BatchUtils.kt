package utils

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.gcrielou.game.Config

/**
 * Created by gcrielou on 05/12/2017.
 */

inline fun Batch.drawSprite(texture: Texture, x: Int, y: Int, spriteSize: Float, spriteX: Int, spriteY: Int, flipX: Boolean = false, flipY: Boolean = false) {
    draw(texture,
            x.toFloat(), y.toFloat(), // world coordinates
            1f, 1f, // world origins
            1f, 1f, // world dimensions
            1f, 1f, // scale
            0f,
            spriteSize.toInt() * spriteX, spriteSize.toInt() * spriteY, //src coordinates
            Config.SPRITE_SIZE.toInt(), Config.SPRITE_SIZE.toInt(), // src dimensions
            flipX, flipY) // flip
}

inline fun Batch.drawSprite(texture: Texture, x: Float, y: Float, spriteSize: Float, spriteX: Int, spriteY: Int, flipX: Boolean = false, flipY: Boolean = false) {
    draw(texture,
            x, y, // world coordinates
            0f, 0f, // world origins
            1f, 1f, // world dimensions
            1f, 1f, // scale
            0f,
            spriteSize.toInt() * spriteX, spriteSize.toInt() * spriteY, //src coordinates
            Config.SPRITE_SIZE.toInt(), Config.SPRITE_SIZE.toInt(), // src dimensions
            flipX, flipY) // flip
}

/*
    inline replace the function directly at compilation so no function is created,
    and therefore, the performance is the same
     */
inline fun Batch.use(action: () -> Unit) {
    begin()
    action()
    end()
}