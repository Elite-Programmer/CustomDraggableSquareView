package com.example.nikidemoapp.ui.customViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.nikidemoapp.R
import com.example.nikidemoapp.model.SquareData

class CustomDrawerView : View {
    private val DEFAULT_COLOR = Color.DKGRAY
    private val DEFAULT_LENGTH = 400
    private val LENGTH_MULTIPLIER = 50.0
    lateinit var listOfSquares: ArrayList<SquareData>
    var parentListener: CustomDrawerViewListener? = null

    private lateinit var mPaint: Paint
    private lateinit var mRect: Rect

    companion object {
        fun getCoordinates(center: Point, length: Int): String {
            val x1 = center.x - length / 2
            val x2 = center.x + length / 2
            val y1 = center.y - length / 2
            val y2 = center.y + length / 2

            return "($x1,$y1) ($x2,$y1) ($x2,$y2) ($x1,$y2)"
        }

        private var mSquareColor: Int = 0
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(set: AttributeSet?) {
        if (set == null) return

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 4F
        mRect = Rect()

        val typedArray = context.obtainStyledAttributes(set,
            R.styleable.CustomDrawerView
        )
        mSquareColor = typedArray.getColor(
            R.styleable.CustomDrawerView_square_color, DEFAULT_COLOR)
        mPaint.color =
            mSquareColor
        typedArray.recycle()

        listOfSquares = ArrayList()
    }

    fun setDrawerListener(listener: CustomDrawerViewListener?) {
        this.parentListener = listener
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            listOfSquares.add(
                SquareData(
                    Point(
                        event.x.toInt(),
                        event.y.toInt()
                    ), DEFAULT_LENGTH
                )
            )
            postInvalidate()
            if (listOfSquares.size >= 1) parentListener?.displaySeekBar()
            parentListener?.initializeSeekBar()
        }

        return false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        listOfSquares.forEach {
            setViewBounds(it)
            canvas.drawRect(mRect, mPaint)
        }
    }

    private fun setViewBounds(it: SquareData) {
        mRect.left = it.center.x - it.length / 2
        mRect.right = it.center.x + it.length / 2
        mRect.top = it.center.y - it.length / 2
        mRect.bottom = it.center.y + it.length / 2
    }

    fun updateLength(multiplier: Int) {
        val lastSquareData = listOfSquares[listOfSquares.size - 1]
        lastSquareData.length =
            (DEFAULT_LENGTH * ((if (multiplier == 0) 1 else multiplier) / LENGTH_MULTIPLIER)).toInt()
        postInvalidate()
    }

    fun getAllSquareData(): List<SquareData>{
        return listOfSquares
    }

    fun restorePreviousSquares(savedSquaresData: List<SquareData>?) {
        listOfSquares = savedSquaresData as ArrayList<SquareData>
        postInvalidate()
    }

    interface CustomDrawerViewListener {
        fun initializeSeekBar()
        fun displaySeekBar()
    }


}