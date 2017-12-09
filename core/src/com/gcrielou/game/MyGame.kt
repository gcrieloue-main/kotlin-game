package com.gcrielou.game

import com.badlogic.gdx.*
import com.badlogic.gdx.audio.Music
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

class MyGame : GameBase() {

    lateinit var batch: SpriteBatch
    private lateinit var spritesCharacter: Texture
    private lateinit var spritesEnv: Texture
    private lateinit var spritesEnemy: Texture
    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: FitViewport
    private lateinit var renderer: ShapeRenderer
    private lateinit var font: BitmapFont

    private lateinit var music: Music
    private lateinit var walkSound: Music

    private var character: Character = Character()
    private var enemy: Enemy = Enemy()
    lateinit var level: Level

    private var displayGrid = false
    private var displayCoords = false
    private var hasMusic = false

    override fun create() {
        batch = SpriteBatch()
        spritesCharacter = Texture("char_sprites.png")
        spritesEnv = Texture("env_sprites.png")
        spritesEnemy = Texture("zombies_and_skeletons.png")
        level = Level(spritesEnv)
        camera = OrthographicCamera()
        viewport = FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        Gdx.input.inputProcessor = this

        val generator = FreeTypeFontGenerator(FileHandle("OpenSans-Regular.ttf"))
        val param = FreeTypeFontGenerator.FreeTypeFontParameter()
        param.size = 16
        font = generator.generateFont(param)
        generator.dispose()

        setSounds()

        if (hasMusic) {
            music.play()
        }
    }

    private fun setSounds() {
        music = Gdx.audio.newMusic(FileHandle("winds_of_stories.mp3"))
        music.volume = 0.7f
        walkSound = Gdx.audio.newMusic(FileHandle("sfx_step_grass_l.mp3"))
        walkSound.volume = 0.2f
    }

    /*
    Called 60 times/second
     */
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
        handleKeys()

        level.draw(batch)
        enemy.drawCharacter(batch, spritesEnemy)

        if (character.isAlive())
            character.drawCharacter(batch, spritesCharacter)

        handleEnemyBehavior()

        if (displayCoords) {
            drawCoords()
        }
    }

    private fun handleEnemyBehavior() {
        val distanceEnemy = distance(Pair(character.positionX, character.positionY), Pair(enemy.positionX, enemy.positionY))
        if (distanceEnemy.toSpriteUnits() < 3) {
            val enemyMoveLength = enemy.computeMoveLength()
            if (character.positionX < enemy.positionX) {
                if (level.canMoveLeft(enemy.positionX, enemy.positionY, enemyMoveLength)) {
                    enemy.moveLeft(enemyMoveLength)
                }
            } else if (character.positionX > enemy.positionX) {
                if (level.canMoveRight(enemy.positionX, enemy.positionY, enemyMoveLength)) {
                    enemy.moveRight(enemyMoveLength)
                }
            }
            if (character.positionY > enemy.positionY) {
                if (level.canMoveUp(enemy.positionX, enemy.positionY, enemyMoveLength)) {
                    enemy.moveUp(enemyMoveLength)
                }
            } else if (character.positionY < enemy.positionY) {
                if (level.canMoveDown(enemy.positionX, enemy.positionY, enemyMoveLength)) {
                    enemy.moveDown(enemyMoveLength)
                }
            }
        }

        if (distanceEnemy.toSpriteUnits() < 1) {
            character.loseHealth()
        }
    }

    private fun handleKeys() {
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
        if (Input.Keys.ALT_LEFT.isKeyPressed()) {
            character.currentState = "FIGHT"
        }
        if (character.currentState.startsWith("RUNNING") && !walkSound.isPlaying) {
            walkSound.play()
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.P -> displayCoords = !displayCoords
            Input.Keys.ENTER -> displayGrid = !displayGrid
            Input.Keys.M -> {
                hasMusic = !hasMusic
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
        spritesCharacter.dispose()
        spritesEnv.dispose()
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
