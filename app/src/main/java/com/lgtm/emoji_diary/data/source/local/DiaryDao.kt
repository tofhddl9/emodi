package com.lgtm.emoji_diary.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lgtm.emoji_diary.data.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Query("SELECT * FROM Diary ORDER BY date DESC")
    fun getDiaries(): Flow<List<Diary>>

    @Query("SELECT * FROM Diary WHERE id = :diaryId")
    suspend fun getDiary(diaryId: Long): Diary
    
    @Insert(onConflict = REPLACE)
    suspend fun insert(diary: Diary)

}