// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #18. Platformer mini-game prototype 😍
// Click 'keyboard' button in the bottom-right corner
// of the plugin's graphical area to activate keyboard listener.
// Use arrow keys to move (Up key to jump).

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
val imageStar = binds["imageStar"] as Image
val imageSmile = binds["imageSmile"] as Image
val imageWall = binds["imageWall"] as Image

val FREE = ' '
val WALL = '#'
val STAR = '*'

val sampleMaze = """
###########
#*   #   *#
#   *#*   #
##  ###  ##
#*       *#
###     ###
#   ***   #
#  #####  #
#*   #   *#
###  #  ###
#*  *#*  *#
###########
"""

// Helper extension function to draw centered image
fun Graphics2D.drawImage(image: Image, x: Int, y: Int, size: Int) =
    drawImage(image, x - size / 2, y - size / 2, size, size, null)

fun String.scrollLeft(counter: Int) = (counter % length).let {
    drop(it) + ' ' + take(it)
}

// Simple game implementation
class Game(inMaze: String) {
    val size = 45
    var score = 0
    val maze = inMaze.trim().lines().map { it.toCharArray() }
    val heightLines = maze.size - 1
    val widthChars = maze[heightLines].size - 1
    val viewMode = Random.nextBoolean()
    val player = Point()
    val enemy = Point(150, -50)
    var jumpCounter = -size / 3

    // values to check in watches window
    override fun toString() = "\n1️⃣player: $player\n2️⃣enemy: $enemy"

    fun calcMazePos(x: Int, y: Int): Pair<Int, Int> {
        val mazeX = Math.round(1.0 * x / size + widthChars / 2)
        val mazeY = Math.round(1.0 * y / size + heightLines / 2)
        return mazeX.toInt() to mazeY.toInt()
    }

    fun getMazePlace(x: Int, y: Int): Char {
        val (mazeX, mazeY) = calcMazePos(x, y)
        return maze[mazeY][mazeX]
    }

    fun setMazePlace(x: Int, y: Int, char: Char) {
        val (mazeX, mazeY) = calcMazePos(x, y)
        maze[mazeY][mazeX] = char
    }

    fun movePoint(point: Point, dx: Int, dy: Int) {
        if (getMazePlace(point.x + dx.sign * size / 2, point.y) != WALL) point.x += dx
        if (getMazePlace(point.x, point.y + dy.sign * size / 2) != WALL) point.y += dy
    }

    fun drawMaze(g2d: Graphics2D) {
        val anim = binds["counter1"] as Int % 20

        for (y in 0..heightLines)
            for (x in 0..widthChars) {
                val dx = -player.x + (x - widthChars / 2) * size
                val dy = -player.y + (y - heightLines / 2) * size

                when (maze[y][x]) {
                    WALL -> { // Shaded wall
                        g2d.drawRect(dx - 16, dy - 14, size - 4, size - 4)
                        g2d.drawImage(imageWall, dx, dy, size)
                    }

                    STAR -> g2d.drawImage(imageStar, dx, dy, size / 2 + anim)
                }
            }
    }

    fun processPlayer(g2d: Graphics2D) {
        val dx = when {
            binds["keyLeft"] == true -> -6
            binds["keyRight"] == true -> 6
            else -> 0
        }

        if (binds["keyUp"] == false)
            jumpCounter = -size / 3

        val dy = when {
            binds["keyUp"] == true -> jumpCounter++
            else -> 7 // Gravity
        }

        movePoint(player, dx, dy)
        if (viewMode) g2d.translate(player.x, player.y)
        g2d.drawImage(imageApple, 0, 0, size)

        if (getMazePlace(player.x, player.y) == STAR) {
            setMazePlace(player.x, player.y, FREE)
            score++
        }
    }

    fun processEnemy(g2d: Graphics2D) {
        val dx = when {
            enemy.x < player.x -> 2
            enemy.x > player.x -> -2
            else -> 0
        }

        val dy = when {
            enemy.y < player.y -> 2
            enemy.y > player.y -> -2
            else -> 0
        }

        movePoint(enemy, dx, dy)

        g2d.drawImage(
            imageSmile,
            -player.x + enemy.x,
            -player.y + enemy.y,
            size
        )
    }
}

// Init and get game instance from the bindings map (script state)
val game = binds.getOrPut("game") {
    Game(sampleMaze)
} as Game

// Set color and stroke width for shadow
graph.stroke = BasicStroke(8f)
graph.color = Color.GRAY

game.processPlayer(graph)
game.drawMaze(graph)
game.processEnemy(graph)

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Arrow keys to move.
Player ${game.player}
Score: ${game.score}"""

// Have fun and amazing results ! 😀👌
