// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #4. Sin/cos polar coordinates pattern 😍
// Use sliders to parametrize script.

import java.awt.*
import kotlin.math.*
import kotlin.random.Random

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get slider values from the bindings map
val sliderRed = binds["sliderRed"] as Int
val sliderGreen = binds["sliderGreen"] as Int

// Get autoincrement counters for animation purposes
val counter1 = binds["counter1"] as Int
val counter001 = binds["counter001"] as Float

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get plugin`s built-in Image instances of predefined sprites
val imageApple = binds["imageApple"] as Image
val imageStar = binds["imageStar"] as Image

// Helper extension functions
fun Graphics2D.drawImage(image: Image, x: Double, y: Double, size: Double) =
    drawImage(image, (x - size / 2).toInt(), (y - size / 2).toInt(), size.toInt(), size.toInt(), null)

fun String.scrollLeft(count: Int) = (count % length).let {
    drop(it) + ' ' + take(it)
}

// Set random color
graph.color = Color(Random.nextInt())

// Draw rays
for (i in 0..360) {
    val s = 1.5 * sin(counter001.toDouble())
    val x = s * i * cos(counter001 + i * sliderRed)
    val y = s * i * sin(counter001 + i * sliderGreen)

    graph.drawLine(x.toInt(), y.toInt(), 0, 0)
}

// Draw sprites
for (i in 0..360) {
    val s = sin(counter001 * 1.0)
    val x = s * i * cos(counter001 + i * sliderRed)
    val y = s * i * sin(counter001 + i * sliderGreen)

    val image = if (i % 2 == 0) imageStar else imageApple
    graph.drawImage(image, x, y, i / 5.0)
}

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Red and green sliders to change visual pattern""".trimIndent()

// Have fun and amazing results ! 😀👌
