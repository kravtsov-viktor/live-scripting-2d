// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

// Sample #6. Snake-trail pattern 😍
// Use sliders to parametrize script.
// Move mouse to animate.

import java.awt.*
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Get slider value from the bindings map
val sliderRed = binds["sliderRed"] as Int

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int

// Get plugin`s built-in Image instances of predefined sprites
val imageApple = binds["imageApple"] as Image
val imageDiamond = binds["imageDiamond"] as Image
val imageBall = binds["imageBall"] as Image
val imageSmile = binds["imageSmile"] as Image

// Helper extension function to draw centered image
fun Graphics2D.drawImage(image: Image, x: Int, y: Int, size: Int) =
    drawImage(image, x - size / 2, y - size / 2, size, size, null)

// Init and get items from the bindings map (script state)
val points = binds.getOrPut("points") {
    MutableList(size = 35) { Point() }
} as MutableList<Point>

points.forEachIndexed { index, point ->
    val size = (sliderRed + sliderRed * sin(0.15 * index)).toInt()

    fun drawFourImages(x: Int, y: Int) {
        graph.drawImage(imageDiamond, x, y, size)
        graph.drawImage(imageApple, -x, -y, size)
        graph.drawImage(imageBall, x, -y, size)
        graph.drawImage(imageSmile, -x, y, size)
    }

    drawFourImages(point.x, point.y)
    drawFourImages(point.x / 2, point.y / 2)
}

points.removeAt(0)
points.add(Point(mousePos))

// Colored title message
graph.color = Color(counter1 * -500)
"""Move mouse to change pattern ! 
Red slider to change sprite size.
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
