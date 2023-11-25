// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

// Sample #13. Lissajous curves 4x4 😍
// Use sliders to parametrize script.

import java.awt.*
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get autoincrement counters for animation purposes
val counter1 = binds["counter1"] as Int
val counter01 = binds["counter01"] as Float
val counter001 = binds["counter001"] as Float

// Get slider values from the bindings map
val sliderRed = binds["sliderRed"] as Int
val sliderGreen = binds["sliderGreen"] as Int
val sliderBlue = binds["sliderBlue"] as Int

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Set stroke width
graph.stroke = BasicStroke(sliderBlue.absoluteValue / 20f)

val len = 1000
val xArr = IntArray(len)
val yArr = IntArray(len)

fun drawLissajous(kx: Int, ky: Int, dx: Int, dy: Int) {
    val sign = if ((kx + ky) % 2 == 0) -1 else 1
    graph.color = Color(counter1 * 1000 * sign)

    var i = 0f
    for (k in 0..len - 1) {
        val radius = 70 + 20 * sin(counter01 + kx + ky)

        val x = radius * cos(i * kx + counter01 / 4)
        val y = radius * sin(i * ky + counter001)

        xArr[k] = dx + x.toInt()
        yArr[k] = dy + y.toInt()

        i += 0.0125f
    }
    graph.drawPolyline(xArr, yArr, len)
}

for (x in 1..4)
    for (y in 1..4)
        drawLissajous(
            kx = sliderRed / x / 5,
            ky = sliderGreen / y / 5,
            dx = 200 * x - 500,
            dy = 200 * y - 500
        )

// Title message
"Use sliders to change visual pattern"

// Have fun and amazing results ! 😀👌
