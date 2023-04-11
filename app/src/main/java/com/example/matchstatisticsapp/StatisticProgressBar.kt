package com.example.matchstatisticsapp

import android.app.Application
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import androidx.core.content.res.ResourcesCompat

class StatisticProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val team1Paint: Paint = Paint()
    private val team2Paint: Paint = Paint()
    private val drawPaint: Paint = Paint()
    private var textPaint = TextPaint()

    private lateinit var container: RectF
//    private var currentProgressValue: Float = 0.0f

    private var typedArray: TypedArray? = null

    private var winsTeam1 = 0f
    private var teamWins2 = 0f
    private var drawTeams = 0f

    init {
        typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StatisticBar,
            0,
            0
        )
        initAttributes()
    }

    private fun initAttributes() {
        typedArray?.apply {
            team1Paint.color =
                getColor(R.styleable.StatisticBar_team1Color, Color.GREEN)

            team2Paint.color =
                getColor(R.styleable.StatisticBar_team2Color, Color.BLUE)

            drawPaint.color =
                getColor(R.styleable.StatisticBar_drawColor, Color.GRAY)

            textPaint.color = getColor(
                R.styleable.StatisticBar_textColor,
                Color.WHITE
            )
            textPaint.textSize =
                getDimensionPixelSize(R.styleable.StatisticBar_textSize, 32).toFloat()

            val fontId = getResourceId(R.styleable.StatisticBar_android_fontFamily, 0)
            if (fontId != 0) {
                textPaint.typeface = ResourcesCompat.getFont(context, fontId)
            } else {
                textPaint.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
            }

            recycle()
        }
    }

    fun setProgress(
        @FloatRange(from = 0.0, to = 100.0) com1Value: Float = 50f,
        @FloatRange(from = 0.0, to = 100.0) com2Value: Float = 50f,
        @FloatRange(from = 0.0, to = 100.0) com3Value: Float = 0f
    ) {
        winsTeam1 = com1Value
        teamWins2 = com2Value
        drawTeams = com3Value
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        container = RectF()
        container.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawProgressBar(canvas)
    }

    private fun drawProgressBar(canvas: Canvas?) {
        with(container){

            val textSizeWidth =
                textPaint.measureText("%s%%".format(winsTeam1.toInt()))

            val rightX1 = ((winsTeam1 / 100.0f) * container.right) - 10
            val leftX1 = ((winsTeam1 / 100.0f) * container.right) + 10
            val rightX2 = (((winsTeam1 + drawTeams) / 100.0f) * container.right) - 10
            val leftX2 = (((winsTeam1 + drawTeams) / 100.0f) * container.right) + 10

            canvas?.drawRoundRect(
                left,
                top,
                rightX1,
                bottom,
                20f,
                20f,
                team1Paint
            )
            if (winsTeam1 >= 10)
                canvas?.drawText(
                    "%s%%".format(winsTeam1.toInt()),
                    (left + rightX1)/2 - textSizeWidth / 2,
                    centerY() + textPaint.textSize / 2,
                    textPaint
                )

            if (drawTeams != 0f) {
                canvas?.drawRoundRect(
                    leftX1,
                    top,
                    rightX2,
                    bottom,
                    20f,
                    20f,
                    drawPaint
                )
                if (drawTeams >= 10)
                    canvas?.drawText(
                        "%s%%".format(drawTeams.toInt()),
                        (leftX1 + rightX2)/2 - textSizeWidth / 2,
                        container.centerY() + textPaint.textSize / 2,
                        textPaint
                    )
            }

            canvas?.drawRoundRect(
                leftX2,
                top,
                right,
                bottom,
                20f,
                20f,
                team2Paint
            )
            if (teamWins2 >= 10)
                canvas?.drawText(
                    "%s%%".format(teamWins2.toInt()),
                    (right + leftX2)/2 - textSizeWidth / 2,
                    container.centerY() + textPaint.textSize / 2,
                    textPaint
                )
        }
    }
}