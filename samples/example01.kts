// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

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

// Helper extension functions

fun Graphics2D.drawImage(image: Image, x: Float, y: Float, size: Float) =
    drawImage(image, (x - size / 2).toInt(), (y - size / 2).toInt(), size.toInt(), size.toInt(), null)

fun String.scrollLeft(counter: Int) = (counter % length).let {
    drop(it) + ' ' + take(it)
}

fun drawPolyline(kx: Int, ky: Int, stroke: Int, size: Int) {
    graph.stroke = BasicStroke(stroke.absoluteValue / 30f)
    graph.color = Color(counter1 * 1000)

    val len = 3000
    val xArr = IntArray(len)
    val yArr = IntArray(len)

    var k = 0f
    for (i in 0 until len) {
        val radius = size + size / 2 * sin(counter001 * 3)
        val x = radius * cos(k * kx / 500 + counter001)
        val y = radius * sin(k * ky / 500 + counter001)

        xArr[i] = x.toInt()
        yArr[i] = y.toInt()
        k += 0.025f
    }

    graph.drawPolyline(xArr, yArr, len)
}

fun drawSprites(kx: Int, ky: Int, size: Int) {
    var i = -6f
    while (i <= 6) {
        val radi = size + size / 2 * sin(counter01 / 2)
        val x = 2 * radi * cos(i * kx / 150 + counter001)
        val y = 2 * radi * sin(i * ky / 150 + counter001)

        graph.drawImage(imageDiamond, x, y, radi)
        i += 0.125f
    }
}

if (sliderBlue > 0)
    drawSprites(sliderRed, sliderGreen, sliderBlue)
else
    drawPolyline(sliderRed, sliderGreen, sliderYellow, -sliderBlue)

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -1000)
"""$advert
Move red and green sliders to change pattern
Blue slider to change size and mode (line vs sprites)"""

// Have fun and amazing results ! 😀👌
