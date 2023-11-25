// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

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

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

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
    drawLine(0, 0, mousePos.x, mousePos.y)
}

fun drawAlphaAngle() = with(graph) {
    stroke = BasicStroke(5f)
    color = MAGENTA
    drawString("alpha $alphaStr°", mousePos.x + 8, mousePos.y - 8)
    drawArc(-radius / 2, -radius / 2, radius, radius, 0, alphaDeg.toInt())

    drawLine(0, 0, rx, ry)
    drawOval(rx - 4, ry - 4, 8, 8)
}

fun drawSinCos() = with(graph) {
    stroke = BasicStroke(5f)

    color = GRAY
    drawLine(rx, ry, rx, 0)
    drawString("sin", rx + 8, ry / 2)

    color = BLUE
    drawLine(rx, ry, 0, ry)
    drawString("cos", rx / 2, ry - 8)
}

fun drawSecCosecTanCotan() = with(graph) {
    stroke = BasicStroke(5f)

    if (cos.absoluteValue > 0.000001) {
        val radSec = (radius * sec).toInt()

        color = RED
        drawLine(0, 0, radSec, 0)
        drawString("sec", radSec / 2, 0)

        color = CYAN
        drawLine(rx, ry, radSec, 0)
        drawString("tan", rx / 2 + radSec / 2, ry / 2)
    }

    if (sin.absoluteValue > 0.000001) {
        val radCosec = (radius * cosec).toInt()

        color = GREEN
        drawLine(0, 0, 0, radCosec)
        drawString("cosec", 0, radCosec / 2)

        color = ORANGE
        drawLine(rx, ry, 0, radCosec)
        drawString("cotan", rx / 2, ry / 2 + radCosec / 2)
    }
}

val radius = 220
val alphaRad = atan2(mousePos.y.toDouble(), mousePos.x.toDouble())
val alphaDeg = toDegrees(-alphaRad)
val cos = cos(alphaRad)
val sin = sin(alphaRad)
val tan = -tan(alphaRad)
val cotan = -1 / tan(alphaRad)
val sec = 1 / cos
val cosec = 1 / sin

val alphaStr = String.format("%.1f", alphaDeg)
val sinStr = String.format("%.1f", sin)
val cosStr = String.format("%.1f", cos)
val tanStr = String.format("%.1f", -tan)
val cotanStr = String.format("%.1f", -cotan)
val secStr = String.format("%.1f", sec)
val cosecStr = String.format("%.1f", -cosec)

val rx = (radius * cos).toInt()
val ry = (radius * sin).toInt()

drawSinCos()
drawSecCosecTanCotan()
drawUnitCircle()
drawAlphaAngle()

// White title message
graph.color = GRAY
"""angle $alphaStr°
  sin $sinStr
  cos $cosStr
  tan $tanStr
cotan $cotanStr
  sec $secStr
cosec $cosecStr"""

// Have fun and amazing results ! 😀👌
