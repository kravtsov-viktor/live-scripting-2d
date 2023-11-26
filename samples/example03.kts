// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #3. Space flight stars 😃 Working with state.
// Move mouse to change emitter center.
// Red slider to change sprite speed.

import java.awt.*
import kotlin.math.*
import kotlin.random.Random

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int

// Get slider value from the bindings map
val sliderRed = binds["sliderRed"] as Int

// Get plugin`s built-in Image instances of predefined sprites
val imageStar = binds["imageStar"] as Image

val wdt = 300f
val hgt = 300f

// Helper extension functions
fun Graphics2D.drawImage(image: Image, x: Int, y: Int, size: Int) =
    drawImage(image, x - size / 2, y - size / 2, size, size, null)

fun String.scrollLeft(count: Int) = (count % length).let {
    drop(it) + ' ' + take(it)
}

// Simple item implementation
class Item {
    val point = Point()
    var size = 0

    var initX = 0f
    var initY = 0f
    var z = -1f

    fun update(speed: Int) {
        z -= speed.absoluteValue

        if (z < 0) {
            z = mapRandom(0f, wdt)
            initX = mapRandom(-wdt / 2f, wdt / 2f)
            initY = mapRandom(-hgt / 2f, hgt / 2f)
        }

        point.x = mapValue(initX / z, 0f, 1f, 0f, wdt).toInt()
        point.y = mapValue(initY / z, 0f, 1f, 0f, hgt).toInt()
        size = mapValue(z, 0f, wdt, 50f, 0f).toInt()
    }

    fun mapRandom(min: Float, max: Float) =
        mapValue(Random.nextFloat(), 0f, 1f, min, max)

    fun mapValue(value: Float, inMin: Float, inMax: Float, outMin: Float, outMax: Float) =
        (value - inMin) * (outMax - outMin) / inMax - inMin + outMin

    override fun toString() = "$point" // value to check in watches window
}

// Init and get items from the bindings map (script state)
val items = binds.getOrPut("items") {
    List(200) { Item() }
} as List<Item>

graph.color = Color(128, 128, 128, 100)

// Draw rays
items.forEach {
    graph.drawLine(it.point.x, it.point.y, mousePos.x, mousePos.y)
}

// Draw sprites
items.forEach {
    graph.drawImage(imageStar, mousePos.x + it.point.x, mousePos.y + it.point.y, it.size)
    it.update(sliderRed / 25)
}

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// Colored title message
graph.color = Color(counter1 * -500)
"""$advert
Move mouse to change center point
Red slider is for stars speed
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
