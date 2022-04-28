package com.lgtm.emoji_diary.data.source

import com.lgtm.emoji_diary.data.Diary
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {

    fun getDiaries(): Flow<Result<List<Diary>>>

    suspend fun getDiary(diaryId: Long): Result<Diary>

    suspend fun insertDiary(diary: Diary)
}