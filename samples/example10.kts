// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

// Sample #10. Parallax stars pattern 💕
// Use sliders to parametrize script.
// Move mouse to animate.
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

// Helper extension function to draw centered image
fun Graphics2D.drawImage(image: Image, point: Point, size: Int) =
    drawImage(image, point.x - size / 2, point.y - size / 2, size, size, null)

val wdt = 700
val hgt = 700

// Simple item implementation
class Item(val image: Image) {
    val point = Point(
        (-wdt..wdt).random(),
        (-hgt..hgt).random()
    )

    val size = (0..100).random()

    fun update() {
        val k = 150 - size
        point.translate(mousePos.x / k, mousePos.y / k)

        if (point.x > wdt || point.x < -wdt) point.x = -point.x
        if (point.y > hgt || point.y < -hgt) point.y = -point.y
    }

    override fun toString() = "$point" // value to check in watches window
}

// Init and get items from the bindings map (script state)
val items = binds.getOrPut("items") {
    List(75) { Item(imageStar) } + List(75) { Item(imageDiamond) }
} as List<Item>

items.forEach {
    graph.drawImage(it.image, it.point, it.size + sliderRed)
    it.update()
}

// Colored title message
graph.color = Color(counter1 * -500)
"""Red slider is for sprites size.
Move mouse to change sprites movement direction.
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
