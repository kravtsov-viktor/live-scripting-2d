// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

// Sample #20. Snake mini-game prototype 😍
// Click 'keyboard' button in the bottom-right corner
// of the plugin's graphical area to activate keyboard listener.
// Use arrow keys to move.

import java.awt.*
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
val imageSmile = binds["imageSmile"] as Image

// Helper extension function to draw centered image
fun Graphics2D.drawImage(image: Image, point: Point, size: Int) =
    drawImage(image, point.x - size / 2, point.y - size / 2, size, size, null)

// Simple game implementation
class Game {
    val size = 55
    var score = 0
    val viewMode = Random.nextBoolean()
    val player = MutableList(5) { Point() }
    val food = createFood(20)

    // Values to check in watches window
    override fun toString() = "\n1️⃣player: $player\n2️⃣food: $food"

    fun createFood(count: Int) = MutableList(count) {
        Point(
            (-5..5).random() * size,
            (-5..5).random() * size
        )
    }

    fun processGameLoop(g2d: Graphics2D) {
        val head = Point(player.last())

        if (viewMode) g2d.translate(-head.x, -head.y)

        if (binds["keyUp"] == true) head.y -= 6
        if (binds["keyDown"] == true) head.y += 6
        if (binds["keyLeft"] == true) head.x -= 6
        if (binds["keyRight"] == true) head.x += 6

        player.forEach {
            g2d.drawImage(imageSmile, it, size)
        }

        val anim = binds["counter1"] as Int % 20
        food.forEach {
            g2d.drawImage(imageApple, it, size + anim)
        }

        val foodCount = food.size
        food.removeIf {
            it.distance(head) < size / 2
        }

        val delta = foodCount - food.size
        if (delta == 0) {
            player.removeAt(0)
        } else {
            score += delta
            food.addAll(createFood(delta))
        }

        player.add(head)
    }
}

// Init and get game instance from the bindings map (script state)
val game = binds.getOrPut("game") {
    Game()
} as Game

game.processGameLoop(graph)

// Colored title message
graph.color = Color(counter1 * -500)
"""Arrow keys to move.
Player head position ${game.player.last()}
Score: ${game.score}"""

// Have fun and amazing results ! 😀👌
