package com.hurryyu.cornerlayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout

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
    private val cornerBannerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val cornerBannerPath:Path = Path()

    private var viewWidth = 0

    private var viewHeight = 0

    /**
     * 边角横幅背景颜色
     */
    private var bannerBackgroundColor = DEFAULT_BANNER_BACKGROUND_COLOR

    /**
     * 最远点占View宽度和高度的百分比
     */
    private var bannerPercent = DEFAULT_BANNER_PERCENT

    /**
     * 边角横幅宽度
     */
    private var bannerWidth = DEFAULT_BANNER_WIDTH

    init {
        initAttrs(attrs)

        cornerBannerPaint.apply {
            color = bannerBackgroundColor
            style = Paint.Style.FILL
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
        }
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
        drawBanner(canvas)
    }

    private fun drawBanner(canvas: Canvas) {
        val bannerRightWithPercent = (viewWidth * bannerPercent)
        val bannerBottomWithPercent = (viewHeight * bannerPercent)

        val x1Point = arrayOf(bannerRightWithPercent - bannerWidth, 0F)
        val x2Point = arrayOf(bannerRightWithPercent, 0F)
        val y1Point = arrayOf(0F, bannerBottomWithPercent - bannerWidth)
        val y2Point = arrayOf(0F, bannerBottomWithPercent)

        cornerBannerPath.apply {
            reset()
            moveTo(x1Point[0],x1Point[1])
            lineTo(y1Point[0],y1Point[1])
            lineTo(y2Point[0],y2Point[1])
            lineTo(x2Point[0],x2Point[1])
        }
        canvas.drawPath(cornerBannerPath, cornerBannerPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
    }

    companion object {
        private val DEFAULT_BANNER_BACKGROUND_COLOR = Color.parseColor("#FF8080")
        private const val DEFAULT_BANNER_PERCENT = 0.4F
        private val DEFAULT_BANNER_WIDTH = 26.dp
    }
}