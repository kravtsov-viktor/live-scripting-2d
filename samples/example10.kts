// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #10. Parallax stars pattern 💕
// Red slider to change sprites size.
// Move mouse to change direction.
// Pan and zoom graphical area using mouse.

import java.awt.*
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get slider value from the bindings map
val sliderRed = binds["sliderRed"] as Int

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int

// Get plugin`s built-in Image instances of predefined sprites
val imageStar = binds["imageStar"] as Image
val imageDiamond = binds["imageDiamond"] as Image

// Helper extension functions
fun Graphics2D.drawImage(image: Image, point: Point, size: Int) =
    drawImage(image, point.x - size / 2, point.y - size / 2, size, size, null)

fun String.scrollLeft(count: Int) = (count % length).let {
    drop(it) + ' ' + take(it)
}

val side = 500
val range = -side..side

// Simple item implementation
class Item(val image: Image) {
    val point = Point(range.random(), range.random())

    val size = (0..70).random()

    fun update() {
        val k = 150 - size
        point.translate(mousePos.x / k, mousePos.y / k)

        if (point.x !in range) point.x *= -1
        if (point.y !in range) point.y *= -1
    }

    override fun toString() = "$point" // value to check in watches window
}

// Init and get items from the bindings map (script state)
val items = binds.getOrPut("items") {
    List(40) { Item(imageStar) } + List(40) { Item(imageDiamond) }
} as List<Item>

items.forEach {
    graph.drawImage(it.image, it.point, it.size + sliderRed)
    it.update()
}

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Red slider is for sprites size.
Move mouse to change sprites movement direction.
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
