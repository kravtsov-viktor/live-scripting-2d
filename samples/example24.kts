// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #24. Classic old school plasma 😲
// Red and green sliders to change pattern.

import java.awt.*
import java.awt.Color.*
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.*
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int
val counter001 = binds["counter001"] as Float

// Get slider values from the bindings map
val sliderRed = binds["sliderRed"] as Int
val sliderGreen = binds["sliderGreen"] as Int

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Helper extension function
fun String.scrollLeft(counter: Int) = (counter % length).let {
    drop(it) + ' ' + take(it)
}

val imageSize = 250
val range = 0 until imageSize

// Init and get image from the bindings map (script state)
val image = binds.getOrPut("image") {
    BufferedImage(imageSize, imageSize, TYPE_INT_RGB)
} as BufferedImage

fun calcPlasma(hueShift: Float, red: Int, green: Int) {
    val r = 7 + red.absoluteValue / 30f
    val g = 7 + green.absoluteValue / 30f

    for (x in range) for (y in range) {
        var value = sin(x / r) + sin(y / g)
        value += sin((x + y) / 16f)
        value += sin(sqrt(0f + x * x + y * y) / 9)
        value /= 10f

        val rgb = HSBtoRGB(hueShift + value, 1.0f, 1.0f)
        image.setRGB(x, y, rgb)
    }
}

// Calc plasma image
calcPlasma(counter001, sliderRed, sliderGreen)

// Draw plasma image
val zoom = 1 + 0.0625 * sin(counter001 * 3)
val sizeZoomed = (imageSize * zoom).toInt()
graph.drawImage(image, -sizeZoomed, -sizeZoomed, sizeZoomed * 2, sizeZoomed * 2, null)

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Red and green sliders to change pattern"""

// Have fun and amazing results ! 😀👌
