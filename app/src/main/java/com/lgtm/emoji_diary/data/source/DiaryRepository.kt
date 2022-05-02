package com.lgtm.emoji_diary.data.source

import com.lgtm.emoji_diary.data.Diary
import kotlinx.coroutines.flow.Flow
import com.lgtm.emoji_diary.data.Result

interface DiaryRepository {

    fun getDiaries(): Flow<Result<List<Diary>>>

    suspend fun getDiary(diaryId: Long): Result<Diary>

    suspend fun insertDiary(diary: Diary)

    suspend fun insertOrUpdateDiary(diary: Diary)

    suspend fun deleteDiary(diaryId: Long)
}