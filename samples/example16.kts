// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #16. 2D hypnotizing interference pattern 😍
// Just colored lines and circles dynamic pattern.
// Use red slider to parametrize script.
// Move mouse to animate.

import java.awt.*
import java.awt.Color.*
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get autoincrement counters for animation purposes
val counter1 = binds["counter1"] as Int
val counter01 = binds["counter01"] as Float

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

    var r = 0.0f
    var rx = 0
    var ry = 0
    var i = 0.0
    while (i < 50) {
        val x = (r * cos(counter01 + i)).toInt()
        val y = (r * sin(counter01 + i)).toInt()
        graph.stroke = BasicStroke(r++ / 20)
        graph.drawLine(dx + rx, dy + ry, dx + x, dy + y)
        rx = x
        ry = y

        i += 0.125
    }
}

val delta = sliderRed * 2

drawPattern(0, 0, RED)
drawPattern(delta, 0, GREEN)
drawPattern(-delta, 0, BLUE)
drawPattern(0, delta, YELLOW)
drawPattern(0, -delta, MAGENTA)
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
