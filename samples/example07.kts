// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

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

// Get autoincrement counters for animation purposes
val counter1 = binds["counter1"] as Int
val counter01 = binds["counter01"] as Float
val counter001 = binds["counter001"] as Float

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

graph.stroke = BasicStroke(sliderGreen.absoluteValue / 10f)

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
    for (i in 0..360 step 360 / sliderRed.absoluteValue) {
        val size = i * sin(counter01 / 2) + if (flag) 200 else 100
        val x = centerX + (size * sin(counter001 + i)).toInt()
        val y = centerY + (size * cos(counter001 + i)).toInt()

        graph.drawLine(centerX, centerY, x, y)
        graph.drawLine(prevX, prevY, x, y)
        prevX = x
        prevY = y
        flag = !flag
    }
}

// Title message
"""Mouse and red slider to change visual pattern.
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
