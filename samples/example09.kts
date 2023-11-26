// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #9. Sin/cos mania: wave patterns everywhere !😍
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

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int
val counter01 = binds["counter01"] as Float

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get plugin`s built-in Image instances of predefined sprites
val imageApple = binds["imageApple"] as Image
val imageSmile = binds["imageSmile"] as Image

val helloString1 = "СЪЕШЬ ЕЩЁ ЭТИХ МЯГКИХ ФРАНЦУЗСКИХ БУЛОК ДА ВЫПЕЙ ЖЕ ЧАЮ!"
val helloString2 = "A QUICK BROWN FOX JUMPS OVER THE LAZY DOG"

val xArr = IntArray(360 * 2 + 1) { it - 360 }
val yArr = IntArray(360 * 2 + 1)

// Helper extension functions
fun Graphics2D.drawImage(image: Image, x: Int, y: Int, size: Int) =
    drawImage(image, x - size / 2, y - size / 2, size, size, null)

fun String.scrollLeft(count: Int) = (count % length).let {
    drop(it) + ' ' + take(it)
}

fun sineText(str: String, delta: Int, k: Int) {
    for (i in str.indices) {
        val c = str[i].toString()
        val x = sliderRed * (i - str.length / 2) / 5
        val y = (sliderBlue * sin(k * i + counter01)).toInt()

        graph.drawString(c, x, delta + y + y)
        graph.drawImage(imageSmile, x, delta + y, 50)

        graph.drawString(c, x, delta - y - y)
        graph.drawImage(imageApple, x, delta - y, 50)
    }
}

fun sineCurve(coeff: Float, delta: Int) {
    graph.stroke = BasicStroke(coeff)

    for (x in -360..360) yArr[x + 360] =
        (delta + sliderRed * sin(coeff * sliderGreen / 3600 * x + counter01)).toInt()

    graph.drawPolyline(xArr, yArr, 360 * 2)
    graph.drawPolyline(yArr, xArr, 360 * 2)
}

sineText(helloString1, 300, 600)
sineText(helloString1, 100, 1200)
sineText(helloString2, -300, 600)
sineText(helloString2, -100, 1200)

sineCurve(0.5f, -500)
sineCurve(1f, -300)
sineCurve(2f, -150)
sineCurve(3f, 0)
sineCurve(4f, 150)
sineCurve(5f, 300)

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Use sliders to modify sin/cos patterns"""

// Have fun and amazing results ! 😀👌
