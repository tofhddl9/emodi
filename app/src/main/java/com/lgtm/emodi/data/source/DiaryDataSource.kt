package com.lgtm.emodi.data.source

import com.lgtm.emodi.data.Diary
import kotlinx.coroutines.flow.Flow
import com.lgtm.emodi.data.Result

interface DiaryDataSource {

    fun getDiaries(count: Int): Flow<Result<List<Diary>>>

    suspend fun getDiary(diaryId: Long): Result<Diary>

    suspend fun insert(diary: Diary): Long

    suspend fun insertOrUpdate(diary: Diary)

    suspend fun delete(diaryId: Long)
}