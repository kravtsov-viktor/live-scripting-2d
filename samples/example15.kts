// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #15. 2D geometry pattern 😍
// Just colored lines and circles dynamic pattern.
// Use red slider to parametrize script.
// Move mouse to animate.

import java.awt.*
import java.awt.Color.*
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int

// Get slider value from the bindings map
val sliderRed = binds["sliderRed"] as Int

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Helper extension function
fun String.scrollLeft(counter: Int) = (counter % length).let {
    drop(it) + ' ' + take(it)
}

fun drawPattern(dx: Int, dy: Int, color: Color) {
    graph.color = color
    for (i in 0..size step 25) {
        graph.stroke = BasicStroke((i / 50).toFloat())
        val delta = i + counter1 % size

        graph.drawLine(dx, dy + delta, dx + delta, dy)
        graph.drawLine(dx, dy - delta, dx + delta, dy)
        graph.drawLine(dx, dy - delta, dx - delta, dy)
        graph.drawLine(dx, dy + delta, dx - delta, dy)

        graph.drawOval(dx - delta / 2, dy - delta / 2, delta, delta)
    }
}

val size = 300 + sliderRed / 3

drawPattern(0, 0, RED)
drawPattern(size, 0, GREEN)
drawPattern(-size, 0, BLUE)
drawPattern(0, size, YELLOW)
drawPattern(0, -size, MAGENTA)
drawPattern(mousePos.x, mousePos.y, CYAN)

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Red slider is for stroke width.
Move mouse to change visual pattern.
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
