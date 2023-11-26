// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #23. Julia set fractal in pop art style 😲
// Red slider for iterations count.
// Red slider value > 0 for Julia else for Cubic Julia
// Move mouse to animate.

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

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Helper extension function
fun String.scrollLeft(counter: Int) = (counter % length).let {
    drop(it) + ' ' + take(it)
}

val imageSize = 525

// Init and get image from the bindings map (script state)
val image = binds.getOrPut("image") {
    BufferedImage(imageSize, imageSize, TYPE_INT_RGB)
} as BufferedImage

// Initial values
val zoom = 1 + 0.125 * sin(counter001 * 3)
val iterations = sliderRed.absoluteValue
val cX = mousePos.x / 100f
val cY = mousePos.y / 100f

// Precalculate values
val halfWdt = imageSize / 2
val halfHgt = imageSize / 2
val halfZoomWdt = halfWdt * zoom
val halfZoomHgt = halfHgt * zoom
val range = 0 until imageSize
val coeff = counter001 * iterations

// Render Julia fractal image
fun renderJulia() {
    for (x in range) for (y in range) {
        var zx = (x - halfWdt) / halfZoomWdt
        var zy = (y - halfHgt) / halfZoomHgt

        var i = iterations
        while (zx * zx + zy * zy < 4 && --i > 0) {
            val tmp = zx * zx - zy * zy + cX
            zy = 2 * zx * zy + cY
            zx = tmp
        }

        val c = HSBtoRGB(coeff / i, 1f, 1f)
        image.setRGB(x, y, c)
    }
}

// Render Julia cube fractal image
fun renderJuliaCube() {
    for (x in range) for (y in range) {
        var zx = (x - halfWdt) / halfZoomWdt
        var zy = (y - halfHgt) / halfZoomHgt

        var i = iterations
        while (zx * zx + zy * zy < 4 && --i > 0) {
            val tmp = zx * zx * zx - zy * zy * zx - 2 * zx * zy * zy + cX
            zy = 3 * zx * zx * zy - zy * zy * zy + cY
            zx = tmp
        }

        val c = HSBtoRGB(coeff / i, 1f, 1f)
        image.setRGB(x, y, c)
    }
}

if (sliderRed > 0) renderJulia() else renderJuliaCube()

// Draw fractal image
graph.drawImage(image, -imageSize / 2, -imageSize / 2, null)

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Red slider for iterations count
Red slider value > 0 for Julia else for Cubic Julia
Move mouse to change visual pattern"""

// Have fun and amazing results ! 😀👌
