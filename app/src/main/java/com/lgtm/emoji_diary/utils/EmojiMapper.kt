package com.lgtm.emoji_diary.utils

import android.content.Context
import com.lgtm.emoji_diary.R
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat


// TODO : Temporary Map :(
object EmojiMapper {
    private val emojiDrawableResMap = mapOf<Long, Int>(
        1L to R.drawable.smile,
        2L to R.drawable.angry,
        3L to R.drawable.cool,
        4L to R.drawable.cry,
        5L to R.drawable.love,
        6L to R.drawable.sad,
        7L to R.drawable.scared,
        8L to R.drawable.soso,
        9L to R.drawable.sleepy,
    )

    fun getEmojiDrawable(context: Context, emojiId: Long): Drawable? {
        return emojiDrawableResMap[emojiId]?.let {
            ResourcesCompat.getDrawable(context.resources, it, null)
        }
    }
}