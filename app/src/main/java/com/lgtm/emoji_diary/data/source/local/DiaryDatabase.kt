package com.lgtm.emoji_diary.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lgtm.emoji_diary.data.Diary

@Database(entities = [Diary::class], version = 1)
abstract class DiaryDatabase : RoomDatabase() {

    abstract fun fooDao(): DiaryDao

}