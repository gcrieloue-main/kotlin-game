package com.gcrielou.game

import com.badlogic.gdx.*
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import utils.*
import java.util.logging.FileHandler

class MyGame : GameBase() {

    lateinit var batch: SpriteBatch
    private lateinit var img: Texture
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: FitViewport
    private lateinit var renderer: ShapeRenderer
    private lateinit var font: BitmapFont

    private lateinit var music: Music
    private lateinit var walkSound: Sound

    private var character: Character = Character()
    lateinit var level: Level

    private var displayGrid = false
    private var displayCoords = false
    private var hasSound = false

    override fun create() {
        batch = SpriteBatch()
        img = Texture("char_sprites.png")
        level = Level(Texture("env_sprites.png"))
        camera = OrthographicCamera()
        viewport = FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        Gdx.input.inputProcessor = this

        val generator = FreeTypeFontGenerator(FileHandle("OpenSans-Regular.ttf"))
        val param = FreeTypeFontGenerator.FreeTypeFontParameter()
        param.size = 16
        font = generator.generateFont(param)
        generator.dispose()

        music = Gdx.audio.newMusic(FileHandle("winds_of_stories.mp3"))
        walkSound = Gdx.audio.newSound(FileHandle("sfx_step_grass_l.mp3"))
    }

    override fun render() {
        clearScreen()
        batch.projectionMatrix = camera.combined

        batch.use {
            draw()
        }

        if (displayGrid) {
            drawGrid()
        }
    }


    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    private fun draw() {
        val distance: Float = character.computePlayerMoveLength()
        if (Input.Keys.DOWN.isKeyPressed()) {
            character.currentState = "RUNNING"
            if (level.canMoveDown(character.positionX, character.positionY, distance))
                character.moveDown(distance)
        }
        if (Input.Keys.UP.isKeyPressed()) {
            character.currentState = "RUNNING"
            if (level.canMoveUp(character.positionX, character.positionY, distance))
                character.moveUp(distance)
        }
        if (Input.Keys.RIGHT.isKeyPressed()) {
            character.currentState = "RUNNING"
            if (level.canMoveRight(character.positionX, character.positionY, distance))
                character.moveRight(distance)
        }
        if (Input.Keys.LEFT.isKeyPressed()) {
            character.currentState = "RUNNING_LEFT"
            if (level.canMoveLeft(character.positionX, character.positionY, distance))
                character.moveLeft(distance)
        }
        if (Input.Keys.SPACE.isKeyPressed()) {
            character.currentState = "JUMP"
        }

        level.draw(batch)
        character.drawCharacter(batch, img)

        if (displayCoords) {
            drawCoords()
        }
    }


    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.P->displayCoords = !displayCoords
            Input.Keys.ENTER -> displayGrid = !displayGrid
            Input.Keys.M -> {
                hasSound = !hasSound
                if (music.isPlaying) music.pause()
                else music.play()
            }
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        character.currentState = "IDLE"
        return false
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
        renderer.dispose()
        music.dispose()
        walkSound.dispose()
        font.dispose()
    }

    private fun drawGrid() {
        renderer.projectionMatrix = camera.combined
        renderer.begin(ShapeRenderer.ShapeType.Line)
        renderer.color = Color.WHITE

        for (y in 0..Config.WORLD_HEIGHT.toInt()) {
            renderer.line(0f, y.toFloat() * Config.SPRITE_SIZE_WORLD_UNIT, Config.WORLD_WIDTH, y.toFloat() * Config.SPRITE_SIZE_WORLD_UNIT)
        }

        for (x in 0..Config.WORLD_WIDTH.toInt()) {
            renderer.line(x.toFloat() * Config.SPRITE_SIZE_WORLD_UNIT, 0f, x.toFloat() * Config.SPRITE_SIZE_WORLD_UNIT, Config.WORLD_HEIGHT)
        }

        renderer.end()
    }

    private fun drawCoords() {
        if (displayCoords) {
            font.draw(batch,
                    "${(character.positionX.toSpriteUnits().floor())},${(character.positionY.toSpriteUnits().floor())} (Sprites floor)",
                    Config.WORLD_HEIGHT - 20f, Config.WORLD_HEIGHT - 20f)
            font.draw(batch,
                    "${(character.positionX.toSpriteUnits())},${(character.positionY.toSpriteUnits())} (Sprites)",
                    Config.WORLD_HEIGHT - 20f, Config.WORLD_HEIGHT - 40f)
            font.draw(batch,
                    "${character.positionX},${character.positionY} (World Units)",
                    Config.WORLD_HEIGHT - 20f, Config.WORLD_HEIGHT - 60f)
        }
    }
}
