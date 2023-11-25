// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

// Sample #19. Shoot 'em all mini-game prototype 😍
// Click 'keyboard' button in the bottom-right corner
// of the plugin's graphical area to activate keyboard listener.
// Use arrow keys to move and space to shoot.

import java.awt.*
import kotlin.math.*
import kotlin.random.Random

// Get explicitly typed bindings map (unresolved bindings is ok)
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get plugin`s built-in Image instances of predefined sprites
val imageApple = binds["imageApple"] as Image
val imageFlower = binds["imageFlower"] as Image
val imageDiamond = binds["imageDiamond"] as Image

// Helper extension functions
fun Graphics2D.drawImage(image: Image, point: Point, size: Int) =
    drawImage(image, point.x - size / 2, point.y - size / 2, size, size, null)

fun Point.moveTo(dest: Point, step: Double = 1.75) {
    val dx = dest.x - x * 1.0
    val dy = dest.y - y * 1.0
    val angle = atan2(dy, dx)
    this.x += (step * cos(angle)).toInt()
    this.y += (step * sin(angle)).toInt()
}

// Simple game implementation
class Game {
    val areaSize = 450
    val size = 50
    var score = 0
    val viewMode = Random.nextBoolean()
    val player = Point()
    val bullets = mutableListOf<Pair<Point, Point>>()
    var enemies = addEnemies(15)

    // Values to check in watches window
    override fun toString() = "\n1️⃣player: $player\n2️⃣enemies: $enemies\n3️⃣bullets: $bullets"

    fun addEnemies(count: Int) = MutableList(count) {
        Point(
            (-areaSize..areaSize).random(),
            (-areaSize..areaSize).random()
        )
    }

    fun processPlayer(g2d: Graphics2D) {
        val dx = when {
            binds["keyRight"] == true -> 3
            binds["keyLeft"] == true -> -3
            else -> 0
        }

        val dy = when {
            binds["keyDown"] == true -> 3
            binds["keyUp"] == true -> -3
            else -> 0
        }

        if (binds["keySpace"] == true && (dx != 0 || dy != 0)) {
            val rx = (-1..1).random()
            val ry = (-1..1).random()
            bullets.add(Point(player) to Point(dx + rx, dy + ry))
        }

        player.translate(dx, dy)
        if (viewMode) g2d.translate(-player.x, -player.y)

        g2d.drawImage(imageApple, player, size * 2)
    }

    fun processEnemiesAndBullets(g2d: Graphics2D) {
        val enemiesCount = enemies.size

        bullets.forEach {
            enemies.removeIf { enemy -> enemy.distance(it.first) < size / 2 }
            g2d.drawImage(imageFlower, it.first, size / 2)
            it.first.translate(it.second.x * 3, it.second.y * 3) // bullet move
        }

        val delta = enemiesCount - enemies.size
        if (delta > 0) {
            score += delta
            enemies.addAll(addEnemies(delta))
        }

        bullets.removeIf {
            it.first.distance(player) > areaSize
        }

        val anim = binds["counter1"] as Int % 20
        enemies.forEach {
            it.moveTo(player) // enemy move
            g2d.drawImage(imageDiamond, it, size + anim)
        }
    }
}

// Init and get game instance from the bindings map (script state)
val game = binds.getOrPut("game") {
    Game()
} as Game

game.processPlayer(graph)
game.processEnemiesAndBullets(graph)

// Colored title message
graph.color = Color(counter1 * -500)
"""Arrow keys to move, Space to fire.
Player ${game.player}
Score: ${game.score}"""

// Have fun and amazing results ! 😀👌
