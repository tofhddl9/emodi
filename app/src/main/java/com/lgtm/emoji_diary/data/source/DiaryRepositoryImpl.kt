package com.lgtm.emoji_diary.data.source

import com.lgtm.emoji_diary.data.Diary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import com.lgtm.emoji_diary.data.Result

class DiaryRepositoryImpl(
    private val diaryLocalDataSource: DiaryDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DiaryRepository {

    override fun getDiaries(count: Int): Flow<Result<List<Diary>>> {
        return diaryLocalDataSource.getDiaries(count)
    }

    override suspend fun getDiary(diaryId: Long): Result<Diary> {
        return diaryLocalDataSource.getDiary(diaryId)
    }

    override suspend fun insertDiary(diary: Diary) {
        diaryLocalDataSource.insert(diary)
    }

    override suspend fun insertOrUpdateDiary(diary: Diary) {
        diaryLocalDataSource.insertOrUpdate(diary)
    }

    override suspend fun deleteDiary(diaryId: Long) {
        diaryLocalDataSource.delete(diaryId)
    }

}