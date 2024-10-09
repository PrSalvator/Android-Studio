package com.example.catandmousegame

import android.content.Context
import android.graphics.Color
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameView(context: Context, val mouseCount: Int, val speed: Int, val size: Int, val screenWidth: Int, val screenHeight: Int) : SurfaceView(context), SurfaceHolder.Callback {
    private val mice = mutableListOf<Mouse>()
    private var totalClicks = 0
    private var hits = 0
    private var startTime = System.currentTimeMillis()

    init {
        holder.addCallback(this)
        // Инициализация мышек
        for (i in 1..mouseCount) {
            mice.add(Mouse(context, size, speed, screenWidth, screenHeight))
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // Запуск игрового цикла
        Thread {
            while (true) {
                val canvas = holder.lockCanvas()
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE) // Очистка экрана
                    // Движение и отрисовка мышек
                    for (mouse in mice) {
                        mouse.move()
                        mouse.draw(canvas)
                    }
                    holder.unlockCanvasAndPost(canvas)
                }
                Thread.sleep(16) // Частота обновления
            }
        }.start()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            totalClicks++
            for (mouse in mice) {
                if (mouse.isClicked(event.x, event.y)) {
                    hits++
                }
            }
        }
        return true
    }

    // Сохранение результатов в БД при окончании игры
    fun saveResults() {
        val endTime = System.currentTimeMillis()
        val gameResult = GameResult(
            clicks = totalClicks,
            hits = hits,
            accuracy = if (totalClicks > 0) hits.toDouble() / totalClicks else 0.0,
            time = (endTime - startTime)/1000
        )
        GlobalScope.launch {
            val db = GameDatabase.getInstance(context).gameDao()
            db.insert(gameResult)
        }
    }

    fun getTotalClicks(): Int {
        return totalClicks
    }

    fun getHits(): Int {
        return hits
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
}