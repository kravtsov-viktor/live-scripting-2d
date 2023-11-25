// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

// Sample #5. Sin/cos sprite pattern 😍
// Use sliders to parametrize script.
// Move mouse to change pattern.

import java.awt.*
import kotlin.math.*
import kotlin.random.Random

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get slider values from the bindings map
val sliderRed = binds["sliderRed"] as Int
val sliderGreen = binds["sliderGreen"] as Int

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int
val counter01 = binds["counter01"] as Float

// Get plugin`s built-in Image instances of predefined sprites
val imageApple = binds["imageApple"] as Image
val imageStar = binds["imageStar"] as Image
val imageFlower = binds["imageFlower"] as Image
val imageDiamond = binds["imageDiamond"] as Image
val imageBall = binds["imageBall"] as Image

// Helper extension function to draw centered image
fun Graphics2D.drawImage(image: Image, x: Float, y: Float, size: Double) =
    drawImage(image, (x - size / 2).toInt(), (y - size / 2).toInt(), size.toInt(), size.toInt(), null)

// Set random color
graph.color = Color(Random.nextInt())

// Draw rays
for (x in -700..700 step 200)
    for (y in -700..700 step 200)
        graph.drawLine(x, y, mousePos.x, mousePos.y)

// Draw sprites
for (x in -700..700 step 100)
    for (y in -700..700 step 100) {
        val image = when {
            x <= 0 && y < 0 -> imageDiamond
            x < 0 && y >= 0 -> imageApple
            x > 0 && y <= 0 -> imageStar
            x >= 0 && y > 0 -> imageFlower
            else -> imageBall
        }

        val dx = mousePos.x - x
        val dy = mousePos.y - y
        val rx = sliderRed * sin(counter01 + x)
        val ry = sliderRed * cos(counter01 + y)

        val size = sliderGreen + rx + ry + sqrt(dx * dx + dy * dy + 0.0) / 4
        graph.drawImage(image, x + rx, y + ry, size)
    }

// Colored title message
graph.color = Color(counter1 * -500)
"""Move mouse and red slider to change animation pattern
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
