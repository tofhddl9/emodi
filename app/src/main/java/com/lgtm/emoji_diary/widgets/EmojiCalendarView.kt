package com.lgtm.emoji_diary.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView
import com.lgtm.emoji_diary.R
import com.lgtm.emoji_diary.utils.toPx

class EmojiCalendarView constructor(
    context: Context,
) : MonthView(context) {

    val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.black)
    }

    val textPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.black)
        textSize = 16.toPx(context).toFloat()
    }

    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean
    ): Boolean {
        canvas.drawRect(x.toFloat(), y.toFloat() , x.toFloat() + mItemWidth , y.toFloat() + mItemHeight, mSelectedPaint)
        return true
    }

    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int, y: Int) {
        canvas.drawCircle(
            x.toFloat() + mItemWidth / 2,
            y + mItemHeight - 3 * 10f,
            16f,
            paint
        )
    }

    override fun onDrawText(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {

        val cx = x + mItemWidth / 2
        val top = y - mItemHeight / 6

        val isInRange = isInRange(calendar)

        if (isSelected) {
            canvas.drawText(
                calendar.day.toString(), cx.toFloat(), mTextBaseLine + top,
                mSelectTextPaint
            )
        }
        else if (hasScheme) {
            canvas.drawText(
                calendar.day.toString(), cx.toFloat(), mTextBaseLine + top,
                if (calendar.isCurrentMonth && isInRange) mSchemeTextPaint else mOtherMonthTextPaint
            )
        }
        else {
            canvas.drawText(
                calendar.day.toString(), cx.toFloat(), mTextBaseLine + top,
                if (calendar.isCurrentDay) mCurDayTextPaint else if (calendar.isCurrentMonth && isInRange) mCurMonthTextPaint else mOtherMonthTextPaint
            )
        }
    }

}