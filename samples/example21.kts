// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #21. Space invaders mini-game prototype 😍
// Click 'keyboard' button in the bottom-right corner
// of the plugin's graphical area to activate keyboard listener.
// Use arrow keys to move and space to shoot.

import java.awt.*

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
val imageBall = binds["imageBall"] as Image
val imageSmile = binds["imageSmile"] as Image

// Helper extension functions
fun Graphics2D.drawImage(image: Image, point: Point, size: Int) =
    drawImage(image, point.x - size / 2, point.y - size / 2, size, size, null)

fun String.scrollLeft(counter: Int) = (counter % length).let {
    drop(it) + ' ' + take(it)
}

// Simple game implementation
class Game {
    val imgSize = 50
    val areaSize = 400
    var score = 0
    val player = Point(0, areaSize - imgSize * 3)
    val bullets = mutableListOf<Point>()
    var enemies = createEnemies(30)

    // Values to check in watches window
    override fun toString() = "\n1️⃣player: $player\n2️⃣enemies: $enemies\n3️⃣bullets: $bullets"

    fun createEnemies(count: Int) = MutableList(count) {
        Point(
            (-areaSize..areaSize).random(),
            (-areaSize..0).random()
        )
    }

    fun processPlayer(g2d: Graphics2D) {
        if (binds["keyUp"] == true) player.y -= 8
        if (binds["keyDown"] == true) player.y += 8
        if (binds["keyLeft"] == true) player.x -= 8
        if (binds["keyRight"] == true) player.x += 8
        if (binds["keySpace"] == true) bullets.add(Point(player))

        g2d.drawImage(imageSmile, player, imgSize * 2)
    }

    fun processBullets(g2d: Graphics2D) {
        bullets.forEach {
            g2d.drawImage(imageBall, it, imgSize / 2)

            // Bullet move (with random increment)
            it.x += (-5..5).random()
            it.y -= 10
        }

        bullets.removeIf {
            it.distance(player) > areaSize * 3
        }
    }

    fun processEnemies(g2d: Graphics2D) {
        val enemiesCount = enemies.size
        bullets.forEach { bullet ->
            enemies.removeIf { it.distance(bullet) < imgSize / 2 }
        }

        val delta = enemiesCount - enemies.size
        if (delta > 0) {
            score += delta
            enemies.addAll(createEnemies(delta))
        }

        val anim = binds["counter1"] as Int % 20
        enemies.forEach {
            it.y++
            if (it.y++ > areaSize) it.y = -areaSize
            g2d.drawImage(imageApple, it, imgSize + anim)
        }
    }
}

// Init and get game instance from the bindings map (script state)
val game = binds.getOrPut("game") {
    Game()
} as Game

game.processEnemies(graph)
game.processBullets(graph)
game.processPlayer(graph)

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Arrow keys to move, Space to fire.
Player ${game.player}
Score: ${game.score}"""

// Have fun and amazing results ! 😀👌
