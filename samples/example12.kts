// Live Scripting 2D plugin 🎉
// for Intellij IDEA and Android Studio 💻

// Kravtsov Viktor Viktorovich 😍
// Taganrog 2023 💖

// Contact me for your feedback, ideas and donations !
// e-mail: kravtsov.viktor@gmail.com ✍️
// telegram: t.me/eye3kravtsov ✍️

// Sample #12. Trigonometric demo 🤔
// School visualisation of basic trigonometric functions.
// Move mouse to animate.
// Pan and zoom graphical area using mouse.

import java.awt.*
import java.awt.BasicStroke.*
import java.awt.Color.*
import java.lang.Math.toDegrees
import kotlin.math.*

// Get explicitly typed bindings map
// Bindings stores script context variables (and script state)
val binds = bindings as javax.script.Bindings

// Get Point instance of the mouse position from the bindings map
val mousePos = binds["mousePos"] as Point

// Get autoincrement counters for animation purposes
val counter1 = binds["counter1"] as Int

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Helper extension function
fun String.scrollLeft(counter: Int) = (counter % length).let {
    drop(it) + ' ' + take(it)
}

fun drawUnitCircle() = with(graph) {
    color = GRAY

    drawOval(-3, -3, 6, 6)
    drawString("(0,0)", -68, 32)

    drawOval(-radius - 3, -3, 6, 6)
    drawString("(-1,0)", -radius - 88, 0)

    drawOval(radius - 3, -3, 6, 6)
    drawString("(1,0)", radius + 10, 0)

    drawOval(-3, -radius - 3, 6, 6)
    drawString("(0,1)", 0, -radius - 20)

    drawOval(-3, radius - 3, 6, 6)
    drawString("(0,-1)", 0, radius + 40)

    stroke = BasicStroke(
        2.5f,
        CAP_BUTT,
        JOIN_BEVEL,
        0.0f,
        floatArrayOf(8.0f, 8.0f),
        0f
    )

    drawOval(-radius, -radius, radius * 2, radius * 2)
}

fun drawAlphaAngle() = with(graph) {
    color = MAGENTA

    drawLine(0, 0, mousePos.x, mousePos.y)

    stroke = BasicStroke(5f)

    drawString("alpha $alphaStr°", mousePos.x + 8, mousePos.y - 8)
    drawArc(-radius / 2, -radius / 2, radius, radius, 0, alphaDeg.toInt())

    drawLine(0, 0, radiX, radiY)
    drawOval(radiX - 4, radiY - 4, 8, 8)
}

fun drawSinCos() = with(graph) {
    stroke = BasicStroke(5f)

    color = RED
    drawLine(radiX, radiY, radiX, 0)
    drawString("sin", radiX + 8, radiY / 2)

    color = BLUE
    drawLine(radiX, radiY, 0, radiY)
    drawString("cos", radiX / 2, radiY - 8)
}

fun drawSecCosecTanCotan() = with(graph) {
    stroke = BasicStroke(5f)

    if (cos.absoluteValue > 0.000001) {
        val radSec = (radius * sec).toInt()

        color = ORANGE
        drawLine(0, 0, radSec, 0)
        drawString("sec", radSec / 2, -8)

        color = CYAN
        drawLine(radiX, radiY, radSec, 0)
        drawString("tan", radiX / 2 + radSec / 2, radiY / 2)
    }

    if (sin.absoluteValue > 0.000001) {
        val radCosec = (radius * cosec).toInt()

        color = GREEN
        drawLine(0, 0, 0, radCosec)
        drawString("cosec", 8, radCosec / 2)

        color = GRAY
        drawLine(radiX, radiY, 0, radCosec)
        drawString("cotan", radiX / 2, radiY / 2 + radCosec / 2)
    }
}

val radius = 220
val alphaRad = atan2(mousePos.y.toDouble(), mousePos.x.toDouble())
val alphaDeg = toDegrees(-alphaRad)
val cos = cos(alphaRad)
val sin = sin(alphaRad)
val tan = tan(alphaRad)
val cotan = 1 / tan(alphaRad)
val sec = 1 / cos
val cosec = 1 / sin

val alphaStr = String.format("%.1f", alphaDeg)
val sinStr = String.format("%.2f", -sin)
val cosStr = String.format("%.2f", cos)
val tanStr = String.format("%.2f", -tan)
val cotanStr = String.format("%.2f", -cotan)
val secStr = String.format("%.2f", sec)
val cosecStr = String.format("%.2f", -cosec)

val radiX = (radius * cos).toInt()
val radiY = (radius * sin).toInt()

drawSinCos()
drawSecCosecTanCotan()
drawUnitCircle()
drawAlphaAngle()

// Advertisement
val advert = "I love Live Scripting 2D ! 💖".scrollLeft(counter1 / 3)
binds["advert"] = advert

// White title message
graph.color = GRAY
"""$advert
angle $alphaStr°
  sin $sinStr
  cos $cosStr
  tan $tanStr
cotan $cotanStr
  sec $secStr
cosec $cosecStr"""

// Have fun and amazing results ! 😀👌
