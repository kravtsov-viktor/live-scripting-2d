// Привет мир ! 👋 Hello world ! 👍
// Kravtsov Viktor Viktorovich 👌 Кравцов Виктор Викторович 😍
// Taganrog ❤️ Таганрог 2023-3023

// Sample #22. Turtle graphics prototype 😍
// Red slider is for stroke width
// Green slider is for tree growth !

import java.awt.*
import java.awt.geom.Point2D
import java.lang.Math.toRadians
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin

// Get explicitly typed bindings map (unresolved red bindings is OK)
// Bindings stores script context variables (script state)
val binds = bindings as javax.script.Bindings

// Get autoincrement counter for animation purposes
val counter1 = binds["counter1"] as Int

// Get slider values from the bindings map
val sliderRed = binds["sliderRed"] as Int
val sliderGreen = binds["sliderGreen"] as Int

// Get Graphics2D instance from the bindings map
// All subsequent drawings will be done via Graphics2D instance
val graph = binds["graphics2d"] as Graphics2D

// Simple turtle implementation
class Turtle {
    lateinit var graph: Graphics2D
    var point = Point2D.Double()
    var angle = 0.0

    infix fun init(graphics: Graphics2D) {
        graph = graphics
        point = Point2D.Double()
        angle = 0.0
    }

    infix fun setColor(color: Color) {
        graph.color = color
    }

    infix fun setColor(color: Int) {
        graph.color = Color(color)
    }

    infix fun setStroke(width: Float) {
        graph.stroke = BasicStroke(width)
    }

    infix fun setStroke(width: Int) {
        graph.stroke = BasicStroke(width.toFloat())
    }

    infix fun moveTo(pos: Point) {
        point.setLocation(pos.x.toDouble(), -pos.y.toDouble())
    }

    infix fun lookAt(newAngle: Int) {
        angle = newAngle.toDouble()
    }

    infix fun rotate(degrees: Int) {
        angle += degrees
    }

    infix fun left(degrees: Int) {
        angle += degrees
    }

    infix fun right(degrees: Int) {
        angle -= degrees
    }

    infix fun rotate(degrees: Double) {
        angle += degrees
    }

    infix fun left(degrees: Double) {
        angle += degrees
    }

    infix fun right(degrees: Double) {
        angle -= degrees
    }

    infix fun forward(steps: Int) {
        val radians = toRadians(-angle)
        val newx = point.x + steps * cos(radians)
        val newy = point.y + steps * sin(radians)

        graph.drawLine(point.x.toInt(), point.y.toInt(), newx.toInt(), newy.toInt())
        graph.drawOval(newx.toInt() - 2, newy.toInt() - 2, 4, 4)

        point.setLocation(newx, newy)
    }

    infix fun backward(steps: Int) {
        forward(-steps)
    }
}

val turtle = binds.getOrPut("turtle") {
    Turtle()
} as Turtle

fun snowFlake1() {
    repeat(6) {
        turtle forward 120
        turtle right 60
        repeat(9) {
            turtle forward 60
            turtle backward 60
            turtle left 60
        }
        turtle right 120
        turtle backward 120
        turtle left 60
    }
}

fun snowFlake2() {
    repeat(10) {
        repeat(2) {
            turtle forward 80
            turtle right 60
            turtle forward 130
            turtle right 120
        }
        turtle right 36
    }
}

fun tree(len: Int) {
    if (len > 5) {
        turtle forward len
        turtle right 25
        tree(len - 5)
        turtle left 50
        tree(len - 5)
        turtle right 25
        turtle backward len
    }
}

fun branch() {
    repeat(3) {
        repeat(3) {
            turtle forward 40
            turtle backward 40
            turtle right 45
        }
        turtle left 90
        turtle backward 35
        turtle left 45
    }
    turtle right 90
    turtle forward 140
}

// Setup the turtle
turtle init graph
turtle setStroke sliderRed.absoluteValue / 25

// pattern 1 (left)
turtle setColor counter1 * -500
turtle moveTo Point(-200, -300)
turtle lookAt counter1
snowFlake1()

// pattern 2 (right)
turtle setColor counter1 * 500
turtle moveTo Point(200, -300)
snowFlake2()

// pattern (top)
turtle setColor counter1 * -2500
turtle moveTo Point(0, 400)
turtle lookAt counter1 * 3
repeat(8) {
    branch()
    turtle left 45
}

// pattern 4 (tree)
turtle setColor counter1 * -1500
turtle moveTo Point(0, -150)
turtle lookAt 90
tree(25 + sliderGreen.absoluteValue / 8) // values greater than 90 may hang your IDE !

"""Red slider is for stroke width,
Green slider is for tree growth !"""

// Have fun and amazing results ! 😀👌
