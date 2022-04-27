package com.lgtm.emoji_diary.data.source.local

import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.data.source.DiaryDataSource
import com.lgtm.emoji_diary.data.source.Result
import com.lgtm.emoji_diary.data.source.Result.Error
import com.lgtm.emoji_diary.data.source.Result.Success
import java.lang.Exception
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DiaryLocalDataSource(
    private val diaryDao: DiaryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DiaryDataSource {

    override suspend fun getDiaries(): Result<List<Diary>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(diaryDao.getDiaries())
        } catch (e: Exception) {
            Error(e)
        }
    }

}