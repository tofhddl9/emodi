package com.lgtm.emoji_diary.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lgtm.emoji_diary.data.FooData

@Database(entities = [FooData::class], version = 1)
abstract class FooDatabase : RoomDatabase() {

    abstract fun fooDao(): FooDao

}