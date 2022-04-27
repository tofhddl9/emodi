package com.lgtm.emoji_diary.view.edit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpleDate(
    val year : Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
) : Parcelable