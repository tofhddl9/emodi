package com.lgtm.emoji_diary.data.source

import com.lgtm.emoji_diary.data.Diary
import kotlinx.coroutines.flow.Flow

interface DiaryDataSource {

    fun getDiaries(): Flow<Result<List<Diary>>>

    suspend fun getDiary(diaryId: Long): Result<Diary>

    suspend fun insert(diary: Diary): Long

    suspend fun insertOrUpdate(diary: Diary)

    suspend fun delete(diaryId: Long)
}