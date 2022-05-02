package com.lgtm.emoji_diary.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.lgtm.emoji_diary.data.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Query("SELECT * from diary WHERE id= :diaryId")
    suspend fun hasDiary(diaryId: Long): Diary?

    @Query("SELECT * FROM Diary ORDER BY date DESC")
    fun getDiaries(): Flow<List<Diary>>

    @Query("SELECT * FROM Diary WHERE id = :diaryId")
    suspend fun getDiary(diaryId: Long): Diary
    
    @Insert(onConflict = IGNORE)
    suspend fun insert(diary: Diary): Long

    @Update(onConflict = REPLACE)
    suspend fun update(diary: Diary): Int

    @Query("DELETE FROM Diary WHERE id = :diaryId")
    suspend fun delete(diaryId: Long)
}