package com.lgtm.emoji_diary.data.source.local

import androidx.room.Dao
import androidx.room.Query
import com.lgtm.emoji_diary.data.Diary

@Dao
interface DiaryDao {

    @Query("SELECT * FROM Diary")
    fun getDiaries(): List<Diary>

}