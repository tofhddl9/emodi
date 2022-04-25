package com.lgtm.emoji_diary.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FooData")
data class FooData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
)