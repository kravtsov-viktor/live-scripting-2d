// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

// Sample #14. Porter-Duff plasma tribute 😲
// Old-school plasma demo using two blending images (no more sin and cos)
// Move mouse to animate.

import java.awt.*
import java.awt.AlphaComposite.SRC_OVER
import java.awt.Color.*
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.*
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

fun createGradientImage(size: Int, colors: Array<Color>, dist: FloatArray) =
    BufferedImage(size, size, TYPE_INT_RGB).also {
        val graphics = it.createGraphics()
        graphics.paint = RadialGradientPaint(
            size / 2.0f,
            size / 2.0f,
            size / 2.0f,
            dist,
            colors,
            MultipleGradientPaint.CycleMethod.REFLECT
        )
        graphics.fillRect(0, 0, size, size)
    }

val imageSize = 700

// Init and get image from the bindings map (script state)
val image = binds.getOrPut("image") {
    createGradientImage(
        size = imageSize,
        colors = arrayOf(BLUE, GREEN, RED, BLUE, ORANGE, RED, BLUE, GREEN, RED, BLUE, ORANGE),
        dist = floatArrayOf(0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1f)
    )
} as Image

val range = -2..1

// Draw first image
for (x in range)
    for (y in range)
        graph.drawImage(image, -mousePos.x + x * imageSize, -mousePos.y + y * imageSize, null)

// Set blending mode
graph.composite = AlphaComposite.getInstance(SRC_OVER, 0.5f)

// Draw second image
for (x in range)
    for (y in range)
        graph.drawImage(image, mousePos.x + x * imageSize, mousePos.y + y * imageSize, null)

// Colored title message
graph.color = Color(counter1 * -5000)
"""Move mouse on the graphics area to change visual pattern
Mouse position $mousePos"""

// Have fun and amazing results ! 😀👌
