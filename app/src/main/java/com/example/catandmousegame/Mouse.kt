package com.example.catandmousegame
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class Mouse(private val context: Context, private val size: Int, private val speed: Int, private val screenWidth: Int, private val screenHeight: Int) {
    // Параметры мышки
    private var x: Float = 0f
    private var y: Float = 0f
    private var targetX: Float = 0f
    private var targetY: Float = 0f
    private var velocityX: Float = 0f
    private var velocityY: Float = 0f
    private val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.mouse).let {
        Bitmap.createScaledBitmap(it, size, size, false) // Масштабируем изображение до нужного размера
    }
    private val paint = Paint()

    init {
        // Инициализация позиции мышки в случайной точке экрана
        x = Random.nextFloat() * 1000  // Начальная позиция X (в пределах экрана)
        y = Random.nextFloat() * 1000  // Начальная позиция Y (в пределах экрана)

        // Случайная точка назначения
        setNewTarget()

        // Настройки кисти для отрисовки мышки
        paint.color = Color.GRAY
    }

    // Установка новой случайной цели для движения мышки
    private fun setNewTarget() {
        targetX = Random.nextFloat() * screenWidth // Случайная точка назначения X в пределах экрана
        targetY = Random.nextFloat() * screenHeight // Случайная точка назначения Y в пределах экрана

        // Рассчитываем направление движения мышки к цели
        val distance = sqrt((targetX - x) * (targetX - x) + (targetY - y) * (targetY - y))
        velocityX = (targetX - x) / distance * speed
        velocityY = (targetY - y) / distance * speed
    }

    // Метод для перемещения мышки
    fun move() {
        // Если мышка близка к цели, выбираем новую случайную цель
        if (sqrt((targetX - x) * (targetX - x) + (targetY - y) * (targetY - y)) < 10) {
            setNewTarget()
        }

        // Обновляем позицию
        x += velocityX
        y += velocityY
    }

    // Метод для отрисовки мышки на экране
    fun draw(canvas: Canvas) {
        val angle = Math.toDegrees(Math.atan2(velocityY.toDouble(), velocityX.toDouble())).toFloat() - 90
        // Сохраняем состояние холста
        canvas.save()

        // Поворачиваем холст к углу
        canvas.rotate(angle, x + size / 2, y + size / 2)

        // Рисуем изображение мыши
        canvas.drawBitmap(bitmap, x, y, null)

        // Восстанавливаем состояние холста
        canvas.restore()
    }

    // Проверка нажатия по мышке
    fun isClicked(touchX: Float, touchY: Float): Boolean {
        // Проверка, попал ли пользователь по мышке
        val distance = sqrt((touchX - x) * (touchX - x) + (touchY - y) * (touchY - y))
        if(distance <= size){
            this.setNewTarget()
            return true
        }
        return false
    }
}
