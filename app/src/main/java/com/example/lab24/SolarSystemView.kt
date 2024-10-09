package com.example.lab24

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.concurrent.thread

class SolarSystemView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs) {

    private val paint = Paint()
    private val planets = mutableListOf<Planet>()

    private var running = true
    private var centerX = 0f // Координаты центра экрана
    private var centerY = 0f

    init {
        paint.color = Color.YELLOW // Солнце
        planets.add(Planet(5f, Color.rgb(243, 255, 128), 1f, 65))
        planets.add(Planet( 10f, Color.rgb(255, 248, 132), 0.75f, 85))
        planets.add(Planet(10f, Color.rgb(40, 255, 54), 0.67f, 105))
        planets.add(Planet( 5.5f, Color.rgb(255, 68, 0), 0.51f, 124))
        planets.add(Planet(25f, Color.rgb(255, 130, 84), 0.28f, 169))
        planets.add(Planet(20f, Color.rgb(255, 252, 138), 0.21f, 218))
        planets.add(Planet(15f, Color.rgb(57, 170, 255), 0.15f, 253))
        planets.add(Planet(15f, Color.rgb(0, 119, 210), 0.12f, 288))

        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                running = true
                thread(start = true) {
                    while (running) {
                        drawSolarSystem()
                        Thread.sleep(16) // 60 FPS
                    }
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                // Обновляем координаты центра при изменении размеров экрана
                centerX = width / 2f
                centerY = height / 2f
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                running = false
            }
        })
    }

    private fun drawSolarSystem() {
        val canvas: Canvas? = holder.lockCanvas()
        if (canvas != null) {
            canvas.drawColor(Color.BLACK) // Задний фон
            drawSun(canvas)
            for (planet in planets) {
                drawOrbit(canvas, planet)
            }
            for (planet in planets) {
                planet.update(centerX, centerY)
                drawPlanet(canvas, planet)
            }

            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawSun(canvas: Canvas) {
        // Солнце в центре экрана
        val paint = Paint()
        paint.color = Color.YELLOW
        canvas.drawCircle(centerX, centerY, 50f, paint)
    }

    private fun drawPlanet(canvas: Canvas, planet: Planet) {
        paint.color = planet.color
        canvas.drawCircle(planet.x, planet.y, planet.size, paint)
    }

    private fun drawOrbit(canvas: Canvas, planet: Planet) {
        // Рисуем орбиту (ось) как окружность вокруг центра
        val paint = Paint()
        paint.color = Color.GRAY
        paint.style = Paint.Style.STROKE // Обводка (без заполнения)
        canvas.drawCircle(centerX, centerY, planet.radius.toFloat(), paint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        running = false
    }
}

data class Planet(val size: Float, var color: Int, private val speed: Float, var radius: Int, var x:Float = 0f, var y:Float = 0f) {
    private var angle = 0f

    fun update(centerX: Float, centerY: Float) {
        angle += speed
        x = centerX + radius * Math.cos(Math.toRadians(angle.toDouble())).toFloat() // Положение по оси X относительно центра
        y = centerY + radius * Math.sin(Math.toRadians(angle.toDouble())).toFloat() // Положение по оси Y относительно центра
    }
}

