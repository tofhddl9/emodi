package com.lgtm.emoji_diary.data.source

import com.lgtm.emoji_diary.data.Diary
import kotlinx.coroutines.flow.Flow
import com.lgtm.emoji_diary.data.Result

interface DiaryDataSource {

    fun getDiaries(count: Int): Flow<Result<List<Diary>>>

    suspend fun getDiary(diaryId: Long): Result<Diary>

    suspend fun insert(diary: Diary): Long

    suspend fun insertOrUpdate(diary: Diary)

    suspend fun delete(diaryId: Long)
}