// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

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

val wdt = 400
val hgt = 400
val initSpeed = 4

// Helper extension function to draw centered image
fun Graphics2D.drawImage(image: Image, point: Point, size: Int) =
    drawImage(image, point.x - size / 2, point.y - size / 2, size, size, null)

// Simple item implementation
class Item(val image: Image) {
    val point = Point()
    val maxSize = (25..100).random()
    var size = -1
    var speed = 0

    fun update() {
        size += speed

        when {
            size > maxSize ->
                speed = -speed

            size < 0 -> {
                point.x = (-wdt..wdt).random()
                point.y = (-hgt..hgt).random()
                size = 0
                speed = initSpeed
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

// Colored title message
graph.color = Color(counter1 * -500)
"""Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
