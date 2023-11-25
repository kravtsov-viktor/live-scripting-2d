// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

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

// Helper extension function to draw centered image
fun Graphics2D.drawImage(image: Image, point: Point, size: Int) =
    drawImage(image, point.x - size / 2, point.y - size / 2, size, size, null)

val wdt = 400
val hgt = 400

// Simple item implementation
class Item(val image: Image) {
    val point = Point(
        (-wdt..wdt).random(),
        (-hgt..hgt).random()
    )

    val size = (1..4).random() * 10
    var dx = (-6..7).random()
    var dy = (-6..7).random()

    fun update(g2d: Graphics2D, dsize: Int) {
        g2d.drawImage(image, point, size + dsize)

        point.translate(dx, dy)
        if (point.x > wdt || point.x < -wdt) dx = -dx
        if (point.y > hgt || point.y < -hgt) dy = -dy
    }

    override fun toString() = "$point" // value to check in watches window
}

// Init and get items from the bindings map (script state)
val items = binds.getOrPut("items") {
    List(50) { Item(imageBall) } + List(50) { Item(imageSmile) }
} as List<Item>

items.forEach {
    it.update(graph, sliderRed)
}

// Colored title message
graph.color = Color(counter1 * -500)
"Red slider is for sprite size"

// Have fun and amazing results ! 😀👌
