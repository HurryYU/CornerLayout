package com.hurryyu.cornerlayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * @author HurryYu
 * https://www.hurryyu.com
 * https://github.com/HurryYu
 * 2020-08-31
 */
class CornerLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * 边角横幅画笔
     */
    private val bannerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 横幅文字画笔
     */
    private val bannerTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 横幅Path
     */
    private val bannerPath: Path = Path()

    private var viewWidth = 0

    private var viewHeight = 0

    /**
     * 用户设置的欲绘制文本内容
     */
    var bannerText = ""
        set(value) {
            field = value
            realDrawBannerText = value
        }

    /**
     * 真正绘制的文字,如果bannerText过长,可能会被裁剪
     */
    private var realDrawBannerText = bannerText

    /**
     * 横幅背景颜色
     */
    var bannerBackgroundColor = DEFAULT_BANNER_BACKGROUND_COLOR

    /**
     * 最远点距离View(0,0)点距离
     */
    var bannerDistanceOriginPointLength = DEFAULT_BANNER_DISTANCE_ORIGIN_POINT_LENGTH

    /**
     * 横幅宽度
     */
    var bannerWidth = DEFAULT_BANNER_WIDTH

    /**
     * 横幅文字颜色
     */
    var bannerTextColor = DEFAULT_BANNER_TEXT_COLOR

    /**
     * 横幅文字大小
     */
    var bannerTextSize = DEFAULT_BANNER_TEXT_SIZE

    /**
     * 是否显示
     */
    var display = true
        set(value) {
            field = value
            invalidate()
        }

    /**
     * 横幅显示的位置:左上右下
     */
    var bannerPosition = BannerPosition.TOP_LEFT
        set(value) {
            field = value
            invalidate()
        }

    private val pointList by lazy {
        mutableListOf<Point>()
    }

    init {
        initAttrs(attrs)

        bannerPaint.apply {
            color = bannerBackgroundColor
            style = Paint.Style.FILL
        }

        bannerTextPaint.apply {
            color = bannerTextColor
            textSize = bannerTextSize.toFloat()
            style = Paint.Style.FILL
            textAlign = Paint.Align.LEFT
        }

        setWillNotDraw(false)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        attrs?.let {
            context.obtainStyledAttributes(it, R.styleable.CornerLayout)
        }?.apply {
            bannerBackgroundColor = getColor(
                R.styleable.CornerLayout_bannerBackgroundColor,
                DEFAULT_BANNER_BACKGROUND_COLOR
            )
            bannerDistanceOriginPointLength = getDimensionPixelSize(
                R.styleable.CornerLayout_bannerDistanceLength,
                DEFAULT_BANNER_DISTANCE_ORIGIN_POINT_LENGTH
            )
            bannerWidth = getDimensionPixelSize(
                R.styleable.CornerLayout_bannerWidth,
                DEFAULT_BANNER_WIDTH
            )
            bannerTextColor = getColor(
                R.styleable.CornerLayout_bannerTextColor,
                DEFAULT_BANNER_TEXT_COLOR
            )
            bannerTextSize = getDimensionPixelSize(
                R.styleable.CornerLayout_bannerTextSize,
                DEFAULT_BANNER_TEXT_SIZE
            )
            bannerText = getString(R.styleable.CornerLayout_bannerText) ?: ""
        }
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
        if (!display) {
            return
        }
        val pointList = generatorPointByPosition()
        drawBanner(canvas, pointList)
        drawText(canvas, pointList)
    }

    /**
     * 绘制横幅
     */
    private fun drawBanner(canvas: Canvas, pointList: List<Point>) {
        bannerPath.apply {
            reset()
            pointList.withIndex().forEach {
                if (it.index == 0) {
                    moveTo(it.value.x, it.value.y)
                } else {
                    lineTo(it.value.x, it.value.y)
                }
            }
        }
        canvas.drawPath(bannerPath, bannerPaint)
    }

    private fun generatorPointByPosition(): List<Point> {
        pointList.clear()
        when (bannerPosition) {
            BannerPosition.TOP_LEFT -> {
                pointList.add(Point(0F, bannerDistanceOriginPointLength - bannerWidth.toFloat()))
                pointList.add(Point(bannerDistanceOriginPointLength - bannerWidth.toFloat(), 0F))
                pointList.add(Point(bannerDistanceOriginPointLength.toFloat(), 0F))
                pointList.add(Point(0F, bannerDistanceOriginPointLength.toFloat()))
            }
            BannerPosition.TOP_RIGHT -> {
                pointList.add(
                    Point(
                        viewWidth - (bannerDistanceOriginPointLength - bannerWidth).toFloat(),
                        0F
                    )
                )
                pointList.add(
                    Point(
                        viewWidth.toFloat(),
                        bannerDistanceOriginPointLength - bannerWidth.toFloat()
                    )
                )
                pointList.add(Point(viewWidth.toFloat(), bannerDistanceOriginPointLength.toFloat()))
                pointList.add(Point(viewWidth.toFloat() - bannerDistanceOriginPointLength, 0F))
            }
            BannerPosition.BOTTOM_LEFT -> {
                pointList.add(Point(0F, viewHeight - bannerDistanceOriginPointLength.toFloat()))
                pointList.add(
                    Point(
                        bannerDistanceOriginPointLength.toFloat(),
                        viewHeight.toFloat()
                    )
                )
                pointList.add(
                    Point(
                        bannerDistanceOriginPointLength - bannerWidth.toFloat(),
                        viewHeight.toFloat()
                    )
                )
                pointList.add(
                    Point(
                        0F,
                        viewHeight - (bannerDistanceOriginPointLength - bannerWidth).toFloat()
                    )
                )
            }
            BannerPosition.BOTTOM_RIGHT -> {
                pointList.add(
                    Point(
                        viewWidth - bannerDistanceOriginPointLength.toFloat(),
                        viewHeight.toFloat()
                    )
                )
                pointList.add(
                    Point(
                        viewWidth.toFloat(),
                        viewHeight - bannerDistanceOriginPointLength.toFloat()
                    )
                )
                pointList.add(
                    Point(
                        viewWidth.toFloat(),
                        viewHeight - (bannerDistanceOriginPointLength - bannerWidth).toFloat()
                    )
                )
                pointList.add(
                    Point(
                        viewWidth - (bannerDistanceOriginPointLength - bannerWidth).toFloat(),
                        viewHeight.toFloat()
                    )
                )
            }
        }
        return pointList
    }

    /**
     * 绘制横幅上的文字
     */
    private fun drawText(canvas: Canvas, pointList: List<Point>) {
        // 测量欲绘制文字宽度
        val bannerTextWidth = bannerTextPaint.measureText(realDrawBannerText)

        // 计算banner最短边长度
        val bannerShortestLength =
            (sqrt(
                2 * (bannerDistanceOriginPointLength - bannerWidth).toDouble().pow(2)
            )).toFloat()
        if (bannerTextWidth > bannerShortestLength) {
            // 如果最短边长度小于欲绘制文字长度,则对欲绘制文字剪裁,直到欲绘制文字比最短边长度小方可绘制文字
            realDrawBannerText = realDrawBannerText.substring(0, realDrawBannerText.length - 1)
            drawText(canvas, pointList)
            return
        }

        // 计算banner最长边长度
        val bannerLongestLength =
            (sqrt(2 * (bannerDistanceOriginPointLength).toDouble().pow(2))).toFloat()

        val hOffset =
            if (bannerPosition == BannerPosition.BOTTOM_LEFT || bannerPosition == BannerPosition.BOTTOM_RIGHT) {
                bannerLongestLength / 2 - bannerTextWidth / 2
            } else {
                bannerShortestLength / 2 - bannerTextWidth / 2
            }

        // 单个直角边长度
        val oneOfTheRightAngleLength = (bannerLongestLength - bannerShortestLength) / 2
        // 计算banner的高度
        val bannerHeight =
            sqrt(bannerWidth.toDouble().pow(2) - oneOfTheRightAngleLength.pow(2)).toFloat()

        val fontMetrics = bannerTextPaint.fontMetrics
        // 计算baseLine偏移量
        val baseLineOffset = (fontMetrics.top + fontMetrics.bottom) / 2
        val vOffset = bannerHeight / 2 - baseLineOffset

        canvas.drawTextOnPath(realDrawBannerText, bannerPath, hOffset, vOffset, bannerTextPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
    }

    data class Point(val x: Float, val y: Float)

    companion object {
        private val DEFAULT_BANNER_BACKGROUND_COLOR = Color.parseColor("#FF8080")
        private val DEFAULT_BANNER_DISTANCE_ORIGIN_POINT_LENGTH = 60.dp
        private val DEFAULT_BANNER_WIDTH = 26.dp
        private const val DEFAULT_BANNER_TEXT_COLOR = Color.WHITE
        private val DEFAULT_BANNER_TEXT_SIZE = 12.sp
    }
}