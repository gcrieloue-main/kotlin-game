package utils

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.gcrielou.game.Config
import com.gcrielou.game.Player

/**
 * Created by gcrielou on 05/12/2017.
 */

inline fun Batch.drawSprite(texture: Texture, x: Int, y: Int, spriteSize: Float, spriteX: Int, spriteY: Int, flipX: Boolean = false, flipY: Boolean = false) {
    draw(texture,
            x.toFloat() * Config.SPRITE_SIZE_WORLD_UNIT, y.toFloat() * Config.SPRITE_SIZE_WORLD_UNIT, // world coordinates
            1f, 1f, // world origins
            Config.SPRITE_SIZE_WORLD_UNIT, Config.SPRITE_SIZE_WORLD_UNIT, // world dimensions
            1f, 1f, // scale
            0f,
            spriteSize.toInt() * spriteX, spriteSize.toInt() * spriteY, //src coordinates
            Config.SPRITE_SIZE.toInt(), Config.SPRITE_SIZE.toInt(), // src dimensions
            flipX, flipY) // flip
}

inline fun Batch.drawSprite(texture: Texture, x: Float, y: Float, spriteSize: Float = Config.SPRITE_SIZE, spriteX: Int, spriteY: Int, flipX: Boolean = false, flipY: Boolean = false) {
    draw(texture,
            x, y, // world coordinates
            0f, 0f, // world origins
            Config.SPRITE_SIZE_WORLD_UNIT, Config.SPRITE_SIZE_WORLD_UNIT, // world dimensions
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

fun drawCoords(player: Player, font: BitmapFont, batch: Batch) {

    font.draw(batch,
            "${(player.positionX.toSpriteUnits().floor())},${(player.positionY.toSpriteUnits().floor())} (Sprites floor)",
            Config.WORLD_HEIGHT - 20f, Config.WORLD_HEIGHT - 20f)
    font.draw(batch,
            "${(player.positionX.toSpriteUnits())},${(player.positionY.toSpriteUnits())} (Sprites)",
            Config.WORLD_HEIGHT - 20f, Config.WORLD_HEIGHT - 40f)
    font.draw(batch,
            "${player.positionX},${player.positionY} (World Units)",
            Config.WORLD_HEIGHT - 20f, Config.WORLD_HEIGHT - 60f)

}