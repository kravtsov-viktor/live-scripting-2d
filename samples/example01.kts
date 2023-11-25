// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

// Sample #1. Lissajous curve 💖
// Classical Lissajous pattern using lines or sprites.
// Use sliders to parametrize script.

import java.awt.*
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get slider values from the bindings map
val sliderRed = binds["sliderRed"] as Int
val sliderGreen = binds["sliderGreen"] as Int
val sliderBlue = binds["sliderBlue"] as Int
val sliderYellow = binds["sliderYellow"] as Int

// Get autoincrement counters for animation purposes
val counter1 = binds["counter1"] as Int
val counter01 = binds["counter01"] as Float
val counter001 = binds["counter001"] as Float

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get plugin`s built-in Image instances of predefined sprites
val imageDiamond = binds["imageDiamond"] as Image

// Helper extension function to draw centered image
fun Graphics2D.drawImage(image: Image, x: Float, y: Float, size: Float) =
    drawImage(image, (x - size / 2).toInt(), (y - size / 2).toInt(), size.toInt(), size.toInt(), null)

fun drawPolyline(kx: Int, ky: Int, size: Int) {
    graph.stroke = BasicStroke(sliderYellow.absoluteValue / 20f)
    graph.color = Color(counter1 * 1000)

    val len = 3000
    val xArr = IntArray(len)
    val yArr = IntArray(len)

    var k = 0f
    for (i in 0..len - 1) {
        val radius = size + size / 2 * sin(counter001 / 2)
        val x = radius * cos(k * kx / 1000 + counter001 * 2)
        val y = radius * sin(k * ky / 1000 + counter001)

        xArr[i] = x.toInt()
        yArr[i] = y.toInt()
        k += 0.025f
    }

    graph.drawPolyline(xArr, yArr, len)
}

fun drawSprites(kx: Int, ky: Int, size: Int) {
    var i = -4f
    while (i <= 4) {
        val radi = size + size / 2 * sin(counter01 / 2)
        val x = 2 * radi * cos(i * kx / 10 + counter001)
        val y = 2 * radi * sin(i * ky / 10 + counter001)

        graph.drawImage(imageDiamond, x, y, radi)
        i += 0.025f
    }
}

if (sliderBlue > 0)
    drawSprites(sliderRed, sliderGreen, sliderBlue)
else
    drawPolyline(sliderRed, sliderGreen, -sliderBlue)

// Colored title message
graph.color = Color(counter1 * -1000)
"""Move sliders to change pattern
Blue slider to change pattern size and mode (line vs sprites)"""

// Have fun and amazing results ! 😀👌
