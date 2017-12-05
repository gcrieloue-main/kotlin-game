package com.gcrielou.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch
import utils.drawSprite

/**
 * Created by gcrielou on 05/12/2017.
 */
public class Level {

    class Tile(var sprite: Sprite) // sprite + behavior

    var texture: Texture = Texture("env_sprites.png")

    var tileWater = Tile(Sprite(1, 1))
    var tileBorderTop = Tile(Sprite(1, 2))
    var tileBorderBottom = Tile(Sprite(1, 2))

    var tileSet: Map<String, Tile> = mapOf("W" to tileWater)

    var level: Array<Array<String>> = arrayOf(arrayOf("W", "W"))

    fun draw(batch: Batch) {
        for (row in level) {
            for (col in row) {
                batch.drawSprite(texture, 50f, 50f, Config.SPRITE_SIZE,
                        tileSet.get(col)?.sprite?.x ?: 0,
                        tileSet.get(col)?.sprite?.y ?: 0)
            }
        }

    }
}
