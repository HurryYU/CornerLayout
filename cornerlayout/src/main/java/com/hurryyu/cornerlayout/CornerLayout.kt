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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBanner(canvas)
    }

    private fun drawBanner(canvas: Canvas) {

        cornerBannerPath.apply {
            reset()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = w
        viewHeight = h
    }

    companion object {
        private val DEFAULT_BANNER_BACKGROUND_COLOR = Color.parseColor("#FF8080")
    }
}