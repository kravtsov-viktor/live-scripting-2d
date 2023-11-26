// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #11. Parallax texts pattern 😍
// Red slider to change font size.
// Move mouse to change direction.
// Pan and zoom graphical area using mouse.

import java.awt.*
import java.awt.Color.*
import kotlin.math.*
import kotlin.random.Random

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

// Helper extension function
fun String.scrollLeft(count: Int) = (count % length).let {
    drop(it) + ' ' + take(it)
}

val side = 500
val range = -side..side
val names = listOf("Viktor 👋", "Tigran 😎", "Sophia 😍", "Marina ❤️", "Hello 👍")

// Simple item implementation
class Item(val title: String) {
    val point = Point(range.random(), range.random())

    val size = 10 * (1..5).random()
    val color = Color(Random.nextInt())

    override fun toString() = "$point" // value to check in watches window

    fun update() {
        point.translate(mousePos.x / size / 3, mousePos.y / size / 3)

        if (point.x !in range) point.x *= -1
        if (point.y !in range) point.y *= -1
    }
}

// Init and get items from the bindings map (script state)
val items = binds.getOrPut("items") {
    List(25) { Item(names.random()) }
} as List<Item>

items.forEach {
    graph.color = it.color
    graph.font = graph.font.deriveFont(it.size + sliderRed * 1f)
    graph.drawString(it.title, it.point.x, it.point.y)
    it.update()
}

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Move mouse over graphics area to change titles movement direction
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
