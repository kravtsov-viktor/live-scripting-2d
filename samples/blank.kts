// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Ready-to-use simple script template to get started !

import java.awt.*
import kotlin.math.absoluteValue
import kotlin.random.Random

// Get explicitly typed bindings map (unresolved red bindings is OK)
// Bindings stores script context variables (script state)
// Below are some key variables every script should get to work properly
val binds = bindings as javax.script.Bindings

// Get autoincrement counters for animation purposes
// Counter steps are: step 1 for counter1,
// step 0.1 for counter01 and step 0.01 for counter001
val counter1 = binds["counter1"] as Int
val counter01 = binds["counter01"] as Float
val counter001 = binds["counter001"] as Float

// Get slider values from the bindings map
// There are four colored sliders on the plugins`s GUI
// Slider values are dynamically changed parameters
// to your scripts for different animation purposes
val sliderRed = binds["sliderRed"] as Int
val sliderGreen = binds["sliderGreen"] as Int
val sliderBlue = binds["sliderBlue"] as Int
val sliderYellow = binds["sliderYellow"] as Int

// Get Point instance of the mouse position
val mousePos = binds["mousePos"] as Point

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
// Graphics2D is a key for any drawings your script does
val graph = binds["graphics2d"] as Graphics2D

// Get plugin`s built-in Image instances of predefined
// sprites for your script animations
val imageApple = binds["imageApple"] as Image
val imageStar = binds["imageStar"] as Image
val imageFlower = binds["imageFlower"] as Image
val imageDiamond = binds["imageDiamond"] as Image
val imageBall = binds["imageBall"] as Image
val imageSmile = binds["imageSmile"] as Image
val imageWall = binds["imageWall"] as Image

// Helper functions
fun Graphics2D.drawImage(image: Image, x: Int, y: Int, size: Int) =
    drawImage(image, x - size / 2, y - size / 2, size, size, null)

fun String.scrollLeft(counter: Int) = (counter % length).let {
    drop(it) + ' ' + take(it)
}

// Set color and stroke width
graph.color = Color(counter1 * 1000)
graph.stroke = BasicStroke(counter1 % 25f + sliderRed.absoluteValue)

// Draw four lines symmetrical to mouse position
graph.drawLine(0, 0, mousePos.x, mousePos.y)
graph.drawLine(0, 0, -mousePos.x, mousePos.y)
graph.drawLine(0, 0, mousePos.x, -mousePos.y)
graph.drawLine(0, 0, -mousePos.x, -mousePos.y)

// Draw four images symmetrical to mouse position
graph.drawImage(imageApple, mousePos.x, mousePos.y, 64)
graph.drawImage(imageSmile, -mousePos.x, -mousePos.y, 64)
graph.drawImage(imageStar, mousePos.x, -mousePos.y, 64)
graph.drawImage(imageDiamond, -mousePos.x, mousePos.y, 64)

// Set random color and stroke width
graph.stroke = BasicStroke(4f)
graph.color = Color(counter1 * -1000)

// Draw confetti
repeat(sliderGreen.absoluteValue * 2) {
    val rx = Random.nextInt(-300, 300)
    val ry = Random.nextInt(-300, 300)
    graph.drawLine(rx, ry, rx, ry)
}

// Simple Foo implementation
class Foo {
    val player = Point()

    fun process(g2d: Graphics2D) {
        if (binds["keyUp"] == true) player.y -= 10
        if (binds["keyDown"] == true) player.y += 10
        if (binds["keyLeft"] == true) player.x -= 10
        if (binds["keyRight"] == true) player.x += 10

        g2d.drawImage(imageFlower, player.x, player.y, 150)
    }

    override fun toString() = "player $player" // value to check in watches window
}

// Init and get Foo instance from the bindings map (script state)
val foo = binds.getOrPut("foo") {
    Foo()
} as Foo

foo.process(graph)

val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)

// Sample values to check in watches window
binds["millisTime"] = System.currentTimeMillis()
binds["nanoTime"] = System.nanoTime()
binds["advert"] = advert

// Title message
"""$advert
Red slider is for stroke width.
Green slider is for confetti count.
Move mouse to change visual pattern.
Arrow keys to move sprite.
Mouse at $mousePos"""

// Have fun and amazing results ! 😀👌
