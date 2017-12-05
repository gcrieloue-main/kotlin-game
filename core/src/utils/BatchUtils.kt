package utils

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch

/**
 * Created by gcrielou on 05/12/2017.
 */

inline fun Batch.drawSprite(texture: Texture, x: Float, y: Float, spriteSize: Float, spriteLine: Int, spriteColumn: Int) {
    draw(texture,
            x, y, // world coordinates
            spriteSize, spriteSize, // world origins
            spriteSize, spriteSize, // world dimensions
            2f, 2f, // scale
            0f,
            spriteSize.toInt() * spriteColumn, spriteSize.toInt() * spriteLine, //src coordinates
            spriteSize.toInt(), spriteSize.toInt(), // src dimensions
            false, false) // flip
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