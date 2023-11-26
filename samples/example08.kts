// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #8. Sparkling sprites 😎
// Random sprite coordinates and smooth inc/dec of sprite size.

import java.awt.*
import java.awt.Color.*
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int

// Get plugin`s built-in Image instances of predefined sprites
val imageApple = binds["imageApple"] as Image
val imageFlower = binds["imageFlower"] as Image

val area = 400

// Helper extension functions
fun Graphics2D.drawImage(image: Image, point: Point, size: Int) =
    drawImage(image, point.x - size / 2, point.y - size / 2, size, size, null)

fun String.scrollLeft(count: Int) = (count % length).let {
    drop(it) + ' ' + take(it)
}

// Simple item implementation
class Item(val image: Image) {
    val point = Point()
    val maxSize = (25..175).random()
    var size = -1
    var speed = 0

    fun update() {
        size += speed

        when {
            size > maxSize -> speed *= -1

            size < 0 -> {
                point.x = (-area..area).random()
                point.y = (-area..area).random()
                speed = (3..6).random()
                size = 0
            }
        }
    }

    override fun toString() = "$point" // value to check in watches window
}

// Init and get items from the bindings map (script state)
val items = binds.getOrPut("items") {
    List(50) { Item(imageFlower) } + List(50) { Item(imageApple) }
} as List<Item>

items.forEach {
    graph.drawImage(it.image, it.point, it.size)
    it.update()
}

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Mouse position $mousePos""".trimIndent()

// Have fun and amazing results ! 😀👌
