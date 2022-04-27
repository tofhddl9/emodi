package com.lgtm.emoji_diary.data.source

import com.lgtm.emoji_diary.data.Diary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class DiaryRepositoryImpl(
    private val diaryLocalDataSource: DiaryDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DiaryRepository {

    override suspend fun getDiaries(): Result<List<Diary>> {
        return diaryLocalDataSource.getDiaries()
    }

}