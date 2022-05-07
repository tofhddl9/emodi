package com.lgtm.emodi.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Diary")
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long = 0,
    val title: String = "",
    val content: String = "",
    val emojiId: Long = 0,
) {

}