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

class MyGame : GameBase() {

    lateinit var batch: SpriteBatch

    private lateinit var spritesCharacter: Texture
    private lateinit var spritesCubicMonster: Texture
    private lateinit var spritesEnv: Texture
    private lateinit var spritesObjects: Texture

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: FitViewport
    private lateinit var renderer: ShapeRenderer
    private lateinit var font: BitmapFont

    private lateinit var music: Music
    private lateinit var walkSound: Music
    private lateinit var gruntSound: Music
    private lateinit var monsterGruntSound: Music
    private lateinit var monsterGruntSound2: Music
    private lateinit var monsterGruntSound3: Music
    private lateinit var eventResolutionSound1: Music
    private lateinit var eventResolutionSound2: Music
    private lateinit var swordSound: Sound
    private lateinit var swordHitSound: Music
    private lateinit var gameOverSound: Music
    private lateinit var takeSwordSound: Music
    private lateinit var monsterDeathSound1: Music

    lateinit var player: Player
    lateinit var level: Level
    lateinit var enemies: MutableList<Character>

    private var displayGrid = false
    private var displayCoords = false
    private var hasMusic = true

    private var isGameOver = false

    private var swordTaken = false

    override fun create() {
        batch = SpriteBatch()
        camera = OrthographicCamera()
        viewport = FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera)
        renderer = ShapeRenderer()
        Gdx.input.inputProcessor = this

        createTextures()
        createFonts()
        createSounds()

        startGame()
    }

    private fun startGame() {
        level = Level(spritesEnv)

        enemies = mutableListOf(
                BlueCubicMonster(spritesCubicMonster, 12, 9)
        )
        player = Player(spritesCharacter)
        player.positionX = 11f.toWorldUnits()
        player.positionY = 12f.toWorldUnits()

        if (hasMusic) {
            music.play()
        }
    }

    private fun createTextures() {
        spritesCharacter = Texture("char_sprites.png")
        spritesEnv = Texture("env_sprites.png")
        spritesCubicMonster = Texture("cubic_monsters.png")
        spritesObjects = Texture("objects.png")
    }

    private fun createFonts() {
        val generator = FreeTypeFontGenerator(FileHandle("OpenSans-Regular.ttf"))
        val param = FreeTypeFontGenerator.FreeTypeFontParameter()
        param.size = 16
        font = generator.generateFont(param)
        generator.dispose()
    }

    private fun createSounds() {
        music = Gdx.audio.newMusic(FileHandle("winds_of_stories.mp3"))
        music.volume = 0.05f

        walkSound = Gdx.audio.newMusic(FileHandle("player/sfx_step_grass_l.mp3"))
        walkSound.volume = 0.2f

        gruntSound = Gdx.audio.newMusic(FileHandle("player/gruntsound.wav"))
        gruntSound.volume = 0.2f

        monsterGruntSound = Gdx.audio.newMusic(FileHandle("monster/cubic_hurt_sound01.wav"))
        monsterGruntSound2 = Gdx.audio.newMusic(FileHandle("monster/cubic_hurt_sound02.wav"))
        monsterGruntSound3 = Gdx.audio.newMusic(FileHandle("monster/cubic_hurt_sound03.wav"))
        monsterGruntSound.volume = 0.2f
        monsterGruntSound2.volume = 0.2f
        monsterGruntSound3.volume = 0.2f

        monsterDeathSound1 = Gdx.audio.newMusic(FileHandle("monster/cubic_death_sound01.wav"))
        monsterDeathSound1.volume = 0.8f

        swordSound = Gdx.audio.newSound(FileHandle("player/sword_sound01.wav"))
        swordHitSound = Gdx.audio.newMusic(FileHandle("player/sword_hit01.wav"))
        swordHitSound.volume = 0.2f

        gameOverSound = Gdx.audio.newMusic(FileHandle("player/game_over01.wav"))
        gameOverSound.volume = 0.2f

        eventResolutionSound1 = Gdx.audio.newMusic(FileHandle("events/resolution01.wav"))
        eventResolutionSound1.volume = 1f
        eventResolutionSound2 = Gdx.audio.newMusic(FileHandle("events/resolution02.wav"))
        eventResolutionSound2.volume = 1f
        takeSwordSound = Gdx.audio.newMusic(FileHandle("events/take_sword_sound.wav"))
        takeSwordSound.volume = 1f
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
        if (!isGameOver && !player.isAlive()) {
            println("Game over")
            isGameOver = true
            gameOverSound.play()
        }

        if (level.levelNumber == 0 && enemies.isEmpty()) {
            level.level1()
            eventResolutionSound1.play()
            enemies = mutableListOf(
                    CubicMonster(spritesCubicMonster, 7, 4),
                    CubicMonster(spritesCubicMonster, 9, 3)
            )
        }

        if (level.levelNumber == 1 && enemies.isEmpty()) {
            level.level2()
            eventResolutionSound1.play()
            enemies = mutableListOf(
                    CubicMonster(spritesCubicMonster, 20, 9),
                    CubicMonster(spritesCubicMonster, 22, 8)
            )
        }

        if (!isGameOver && level.levelNumber == 1 && enemies.isEmpty()) {
            eventResolutionSound2.play()
            isGameOver = true
        }

        level.draw(batch)
        drawEnemies()
        drawSpritesStack()

        if (!swordTaken && player.positionX > 16f.toWorldUnits() && player.positionX < 18f.toWorldUnits()
                && player.positionY > 10f.toWorldUnits() && player.positionY < 12f.toWorldUnits()) {
            swordTaken = true
            takeSwordSound.play()
        }

        if (!swordTaken) {
            batch.drawSprite(spritesObjects, 17, 11, Config.SPRITE_SIZE, 0, 0)
        }

        if (player.isAlive()) {
            player.drawCharacter(batch)
        }

        if (displayCoords) {
            drawCoords()
        }

        if (player.isAlive())
            handleKeys()
    }


    fun drawEnemies() {
        for (enemy in enemies) {
            if (enemy.isAlive()) {
                enemy.drawCharacter(batch)
                handleEnemyBehavior(enemy)
            }
        }
    }

    private fun handleEnemyBehavior(enemy: Character) {
        val distanceEnemy = distance(Pair(player.positionX, player.positionY), Pair(enemy.positionX, enemy.positionY))
        if (distanceEnemy.toSpriteUnits() < 3 && distanceEnemy.toSpriteUnits() >= 0.8) {
            val enemyMoveLength = enemy.computeMoveLength()
            if (player.positionX < enemy.positionX) {
                if (level.canMoveLeft(enemy.positionX, enemy.positionY, enemyMoveLength)) {
                    enemy.moveLeft(enemyMoveLength)
                }
            } else if (player.positionX > enemy.positionX) {
                if (level.canMoveRight(enemy.positionX, enemy.positionY, enemyMoveLength)) {
                    enemy.moveRight(enemyMoveLength)
                }
            }
            if (player.positionY > enemy.positionY) {
                if (level.canMoveUp(enemy.positionX, enemy.positionY, enemyMoveLength)) {
                    enemy.moveUp(enemyMoveLength)
                }
            } else if (player.positionY < enemy.positionY) {
                if (level.canMoveDown(enemy.positionX, enemy.positionY, enemyMoveLength)) {
                    enemy.moveDown(enemyMoveLength)
                }
            }
        } else {
            enemy.hold()
        }

        if (player.isAlive() && distanceEnemy.toSpriteUnits() < 1) {
            player.loseHealth()
            if (!gruntSound.isPlaying)
                gruntSound.play()
        }
    }

    private fun handleKeys() {
        val distance: Float = player.computePlayerMoveLength()
        if (Input.Keys.DOWN.isKeyPressed()) {
            if (level.canMoveDown(player.positionX, player.positionY, distance))
                player.moveDown(distance)
            animateWalkDirt()
        }
        if (Input.Keys.UP.isKeyPressed()) {
            if (level.canMoveUp(player.positionX, player.positionY, distance))
                player.moveUp(distance)
            animateWalkDirt()
        }
        if (Input.Keys.RIGHT.isKeyPressed()) {
            if (level.canMoveRight(player.positionX, player.positionY, distance))
                player.moveRight(distance)
            animateWalkDirt()
        }
        if (Input.Keys.LEFT.isKeyPressed()) {
            if (level.canMoveLeft(player.positionX, player.positionY, distance))
                player.moveLeft(distance)
            animateWalkDirt()
        }
        if (Input.Keys.SPACE.isKeyPressed()) {
            player.currentState = "JUMP"
        }

        if (player.isAlive() && player.currentState.startsWith("RUNNING") && !walkSound.isPlaying) {
            walkSound.play()
        }
    }

    private fun hurtEnemiesArround() {
        for (enemy in enemies) {
            if (enemy.isAlive()) {
                if (player.isHurtingEnemy(enemy)) {
                    swordHitSound.play()
                    enemy.loseHealth()
                    if (enemy.isAlive()) {
                        computeEnemyRecoil(enemy)
                        enemyHurtSound()
                    } else {
                        monsterDeathSound1.play()
                    }
                }
            }
        }
        enemies.removeIf { !it.isAlive() }
    }

    private fun enemyHurtSound() =
            when {
                Math.random() > 0.5 -> monsterGruntSound.play()
                Math.random() > 0.7 -> monsterGruntSound2.play()
                else -> monsterGruntSound3.play()
            }

    private fun computeEnemyRecoil(enemy: Character) {
        val (recoilX, recoilY) = enemy.getEnemyRecoil(player.positionX, player.positionY)
        if (recoilX > 0) {
            if (level.canMoveRight(enemy.positionX, enemy.positionY, recoilX)) {
                enemy.moveRight(recoilX)
            }
        } else {
            if (level.canMoveLeft(enemy.positionX, enemy.positionY, recoilX)) {
                enemy.moveRight(recoilX)
            }
        }

        if (recoilY > 0) {
            if (level.canMoveUp(enemy.positionX, enemy.positionY, recoilY)) {
                enemy.moveDown(recoilY)
            }
        } else {
            if (level.canMoveDown(enemy.positionX, enemy.positionY, recoilY)) {
                enemy.moveDown(recoilY)
            }
        }
    }

    private fun animateSword() {
        var animation: MutableList<PositionedSprite> = mutableListOf()
        when {
            player.orientation == Character.Orientation.LEFT -> {
                animation.add(PositionedSprite(Sprite(0, 2, 1, true), player.positionX, player.positionY + Config.SPRITE_SIZE_WORLD_UNIT, spritesCubicMonster))
                animation.add(PositionedSprite(Sprite(1, 2, 1, true), player.positionX - Config.SPRITE_SIZE_WORLD_UNIT, player.positionY + Config.SPRITE_SIZE_WORLD_UNIT, spritesCubicMonster))
                animation.add(PositionedSprite(Sprite(2, 2, 1, true), player.positionX - Config.SPRITE_SIZE_WORLD_UNIT, player.positionY, spritesCubicMonster))
            }
            player.orientation == Character.Orientation.RIGHT || player.orientation == Character.Orientation.UP -> {
                animation.add(PositionedSprite(Sprite(0, 2), player.positionX, player.positionY + Config.SPRITE_SIZE_WORLD_UNIT, spritesCubicMonster))
                animation.add(PositionedSprite(Sprite(1, 2), player.positionX + Config.SPRITE_SIZE_WORLD_UNIT, player.positionY + Config.SPRITE_SIZE_WORLD_UNIT, spritesCubicMonster))
                animation.add(PositionedSprite(Sprite(2, 2), player.positionX + Config.SPRITE_SIZE_WORLD_UNIT, player.positionY, spritesCubicMonster))
            }
            player.orientation == Character.Orientation.DOWN -> {
                animation.add(PositionedSprite(Sprite(2, 2, 1, true), player.positionX - Config.SPRITE_SIZE_WORLD_UNIT, player.positionY, spritesCubicMonster))
                animation.add(PositionedSprite(Sprite(1, 2, 1, true, flipY = true), player.positionX - Config.SPRITE_SIZE_WORLD_UNIT, player.positionY - Config.SPRITE_SIZE_WORLD_UNIT, spritesCubicMonster))
                animation.add(PositionedSprite(Sprite(0, 2, 1, true), player.positionX + Config.SPRITE_SIZE_WORLD_UNIT / 2, player.positionY - Config.SPRITE_SIZE_WORLD_UNIT, spritesCubicMonster))
            }
            else -> throw IllegalArgumentException("Bad player orientation")
        }
        spritesStack.add(animation)
    }

    var lastWalkAnimation = 0f
    private fun animateWalkDirt() {
        if (lastWalkAnimation > 0.5f) {
            var animation: MutableList<PositionedSprite> = mutableListOf()
            animation.add(PositionedSprite(Sprite(0, 5), player.positionX, player.positionY, spritesCubicMonster))
            animation.add(PositionedSprite(Sprite(1, 5), player.positionX, player.positionY, spritesCubicMonster))
            animation.add(PositionedSprite(Sprite(2, 5), player.positionX, player.positionY, spritesCubicMonster))
            spritesStack.add(animation)
            lastWalkAnimation = 0f
        }
        lastWalkAnimation += Gdx.graphics.deltaTime
    }

    class PositionedSprite(var sprite: Sprite, var x: Float, var y: Float, var texture: Texture)

    var lastSpriteDraw: Float = 0f
    var spritesStack: MutableList<MutableList<PositionedSprite>> = mutableListOf()

    private fun drawSpritesStack() {
        if (spritesStack.isNotEmpty()) {
            for (animation in spritesStack) {
                if (animation.isNotEmpty()) {
                    val positionedSprite = animation.get(0)

                    batch.drawSprite(positionedSprite.texture, positionedSprite.x, positionedSprite.y,
                            Config.SPRITE_SIZE,
                            positionedSprite.sprite.x, positionedSprite.sprite.y, positionedSprite.sprite.flipX, positionedSprite.sprite.flipY)

                    lastSpriteDraw += Gdx.graphics.deltaTime
                    if (lastSpriteDraw > 0.05) {
                        animation.removeAt(0)
                        lastSpriteDraw = 0f
                    }
                }
            }
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
            Input.Keys.ALT_LEFT -> {
                if (player.isAlive() && swordTaken) {
                    player.currentState = "FIGHT"

                    // try sword animation
                    animateSword()
                    swordSound.play()
                    hurtEnemiesArround()
                }
            }
            Input.Keys.R -> {
                if (isGameOver) {
                    println("restart")
                    isGameOver = false
                    startGame()
                }
            }
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        player.currentState = "IDLE"
        return false
    }

    override fun dispose() {
        batch.dispose()
        spritesCharacter.dispose()
        spritesEnv.dispose()
        spritesObjects.dispose()
        renderer.dispose()
        music.dispose()
        walkSound.dispose()
        monsterGruntSound.dispose()
        monsterDeathSound1.dispose()
        monsterGruntSound2.dispose()
        monsterGruntSound3.dispose()
        gameOverSound.dispose()
        swordHitSound.dispose()
        takeSwordSound.dispose()
        swordSound.dispose()
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
                    "${(player.positionX.toSpriteUnits().floor())},${(player.positionY.toSpriteUnits().floor())} (Sprites floor)",
                    Config.WORLD_HEIGHT - 20f, Config.WORLD_HEIGHT - 20f)
            font.draw(batch,
                    "${(player.positionX.toSpriteUnits())},${(player.positionY.toSpriteUnits())} (Sprites)",
                    Config.WORLD_HEIGHT - 20f, Config.WORLD_HEIGHT - 40f)
            font.draw(batch,
                    "${player.positionX},${player.positionY} (World Units)",
                    Config.WORLD_HEIGHT - 20f, Config.WORLD_HEIGHT - 60f)
        }
    }
}
