package com.lgtm.emodi.data.source

import com.lgtm.emodi.data.Diary
import kotlinx.coroutines.flow.Flow
import com.lgtm.emodi.data.Result

interface DiaryRepository {

    fun getDiaries(count: Int = 999): Flow<Result<List<Diary>>>

    suspend fun getDiary(diaryId: Long): Result<Diary>

    suspend fun insertDiary(diary: Diary)

    suspend fun insertOrUpdateDiary(diary: Diary)

    suspend fun deleteDiary(diaryId: Long)
}