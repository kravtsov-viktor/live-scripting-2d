// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #2. Bouncing balls demo 👍
// Random sprite coordinates and random increments.
// Inverting increment sign (plus vs minus) when coordinates is out of bounds.
// Use sliders to parametrize script.

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

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int

// Get plugin`s built-in Image instances of predefined sprites
val imageBall = binds["imageBall"] as Image
val imageSmile = binds["imageSmile"] as Image

// Helper extension functions
fun Graphics2D.drawImage(image: Image, point: Point, size: Int) =
    drawImage(image, point.x - size / 2, point.y - size / 2, size, size, null)

fun String.scrollLeft(count: Int) = (count % length).let {
    drop(it) + ' ' + take(it)
}

val side = 400
val range = -side..side

// Simple item implementation
class Item(val image: Image) {
    val point = Point(range.random(), range.random())

    val size = (1..3).random() * 15
    var dx = (-6..7).random()
    var dy = (-6..7).random()

    fun update(g2d: Graphics2D, dsize: Int) {
        g2d.drawImage(image, point, size + dsize)

        point.translate(dx, dy)
        if (point.x !in range) dx *= -1
        if (point.y !in range) dy *= -1
    }

    override fun toString() = "$point" // value to check in watches window
}

// Init and get items from the bindings map (script state)
val items = binds.getOrPut("items") {
    List(40) { Item(imageBall) } + List(40) { Item(imageSmile) }
} as List<Item>

items.forEach {
    it.update(graph, sliderRed)
}

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Red slider is for sprite size"""

// Have fun and amazing results ! 😀👌
