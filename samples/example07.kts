// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #7. Geometry shapes demo 👍
// Use sliders to parametrize script.
// Move mouse to change graphical pattern.

import com.intellij.ui.JBColor
import java.awt.*
import kotlin.math.*
import kotlin.random.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get slider values from the bindings map
val sliderRed = binds["sliderRed"] as Int
val sliderGreen = binds["sliderGreen"] as Int
val sliderBlue = binds["sliderBlue"] as Int

// Get autoincrement counters for animation purposes
val counter1 = binds["counter1"] as Int
val counter01 = binds["counter01"] as Float
val counter001 = binds["counter001"] as Float

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Helper extension function
fun String.scrollLeft(count: Int) = (count % length).let {
    drop(it) + ' ' + take(it)
}

graph.stroke = BasicStroke(1 + sliderGreen.absoluteValue / 150f)

drawShape(mousePos.x, mousePos.y, JBColor.YELLOW)
drawShape(0, 0, JBColor.RED)

val color1 = Color(counter1 * 1000)
drawShape(200, 200, color1)
drawShape(-200, -200, color1)
drawShape(-200, 200, color1)
drawShape(200, -200, color1)

val color2 = Color(counter1 * 5000)
drawShape(0, -400, color2)
drawShape(0, 400, color2)
drawShape(400, 0, color2)
drawShape(-400, 0, color2)

fun drawShape(centerX: Int, centerY: Int, color: Color) {
    graph.color = color

    var flag = true
    var prevX = centerX
    var prevY = centerY

    var i = 0.0
    val step = 360.0 / sliderRed.absoluteValue
    while (i < 360.0) {
        val r = 1 + sin(counter01 / 2) / 2
        val size = if (flag) sliderBlue else sliderBlue + 100

        val x = centerX + (r * size * sin(counter001 + i)).toInt()
        val y = centerY + (r * size * cos(counter001 + i)).toInt()

        graph.drawLine(centerX, centerY, x, y)
        graph.drawLine(prevX, prevY, x, y)
        prevX = x
        prevY = y
        flag = !flag

        i += step
    }
}

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Title message
"""$advert
Sliders to change visual pattern.
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
