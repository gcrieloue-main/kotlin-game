package com.gcrielou.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.utils.viewport.FitViewport

/**
 * Created by gilles on 03-Dec-17.
 */


class Game : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var img: Texture

    lateinit var camera: OrthographicCamera

    lateinit var viewport: FitViewport

    override fun create() {
        batch = SpriteBatch()
        img = Texture("sprites01.png")
        camera = OrthographicCamera()
        // camera coordinates must start in the bottom left

        viewport = FitViewport(1080f, 720f, camera)
    }

    override fun render() {
        clearScreen()
        batch.projectionMatrix = camera.combined
        batch.use { draw() }

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    private var lastAnimationDrawing: Float = 0f
    private var currentSprite = 0

    private fun draw() {

        var spriteSizePx = 16
        var spriteSize = 16f
        var scale = 2f

        val deltaTime = Gdx.graphics.deltaTime
        val speep = 0.1

        if (lastAnimationDrawing >= speep) {
            lastAnimationDrawing = 0f
            val spriteStart = 1
            val spriteEnd = 5
            currentSprite = (currentSprite + spriteStart) % spriteEnd + 1
        } else lastAnimationDrawing += deltaTime

        batch.draw(img, 200f, 100f,
                spriteSize * 3, spriteSize,
                spriteSize, spriteSize,
                scale, scale,
                0f,
                spriteSizePx * currentSprite, spriteSizePx,
                spriteSizePx, spriteSizePx,
                false, false)

        batch.draw(img, 200f, 100f + 2 * spriteSize,
                spriteSize * 3, spriteSize,
                spriteSize, spriteSize,
                scale, scale,
                0f,
                spriteSizePx * currentSprite, spriteSizePx * 2,
                spriteSizePx, spriteSizePx,
                false, false)

        batch.draw(img, 200f, 100f + 4 * spriteSize,
                spriteSize * 3, spriteSize,
                spriteSize, spriteSize,
                scale, scale,
                0f,
                spriteSizePx * currentSprite, spriteSizePx * 3,
                spriteSizePx, spriteSizePx,
                false, false)

        batch.draw(img, 200f, 100f + 6 * spriteSize,
                spriteSize * 3, spriteSize,
                spriteSize, spriteSize,
                scale, scale,
                0f,
                spriteSizePx * currentSprite, spriteSizePx * 4,
                spriteSizePx, spriteSizePx,
                false, false)
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

    private fun clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}
