package com.example.trainingmirror

import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

// 1. النشاط الرئيسي لتشغيل الشاشة
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ExtendedPathPhysicsView(this))
    }
}

// 2. محرك رسم خط المسار الممتد حتى الجيب
class ExtendedPathPhysicsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val cueBall = PointF(300f, 800f)     
    private val targetBall = PointF(600f, 500f)  
    private val aimPoint = PointF(600f, 500f)    

    private val cuePaint = Paint().apply { 
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true 
    }
    
    private val targetPaint = Paint().apply { 
        color = Color.RED
        style = Paint.Style.FILL
        isAntiAlias = true 
    }

    private val mainAimPaint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 6f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val targetPathPaint = Paint().apply {
        color = Color.YELLOW
        strokeWidth = 5f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(15f, 15f), 0f)
        isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val dx = aimPoint.x - cueBall.x
        val dy = aimPoint.y - cueBall.y
        val angle = atan2(dy.toDouble(), dx.toDouble())

        val extensionDistance = 2500f
        val endCueX = cueBall.x + (cos(angle) * extensionDistance).toFloat()
        val endCueY = cueBall.y + (sin(angle) * extensionDistance).toFloat()

        canvas.drawLine(cueBall.x, cueBall.y, endCueX, endCueY, mainAimPaint)

        val targetDx = targetBall.x - cueBall.x
        val targetDy = targetBall.y - cueBall.y
        val targetAngle = atan2(targetDy.toDouble(), targetDx.toDouble())

        val endTargetX = targetBall.x + (cos(targetAngle) * extensionDistance).toFloat()
        val endTargetY = targetBall.y + (sin(targetAngle) * extensionDistance).toFloat()

        canvas.drawLine(targetBall.x, targetBall.y, endTargetX, endTargetY, targetPathPaint)

        canvas.drawCircle(cueBall.x, cueBall.y, 30f, cuePaint)
        canvas.drawCircle(targetBall.x, targetBall.y, 30f, targetPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                aimPoint.set(event.x, event.y)
                invalidate()
            }
        }
        return true
    }
}
