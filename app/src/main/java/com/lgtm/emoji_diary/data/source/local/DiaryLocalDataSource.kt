package com.lgtm.emoji_diary.data.source.local

import android.util.Log
import com.lgtm.emoji_diary.data.Diary
import com.lgtm.emoji_diary.data.source.DiaryDataSource
import com.lgtm.emoji_diary.data.source.Result
import com.lgtm.emoji_diary.data.source.Result.Error
import com.lgtm.emoji_diary.data.source.Result.Success
import java.lang.Exception
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DiaryLocalDataSource(
    private val diaryDao: DiaryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DiaryDataSource {

    override fun getDiaries(): Flow<Result<List<Diary>>> {
        return diaryDao.getDiaries().map {
            try {
                Success(it)
            } catch (e: Exception) {
                Error(e)
            }
        }
    }

    override suspend fun getDiary(diaryId: Long): Result<Diary> = withContext(ioDispatcher) {
        return@withContext try {
            Success(diaryDao.getDiary(diaryId))
        } catch(e: Exception) {
            Error(e)
        }
    }

    override suspend fun insert(diary: Diary): Long {
        return diaryDao.insert(diary)
    }

    override suspend fun delete(diaryId: Long) {
        diaryDao.delete(diaryId)
    }

    override suspend fun insertOrUpdate(diary: Diary) {
        val hasDiary = diaryDao.hasDiary(diary.id)
        if (hasDiary != null) {
            diaryDao.update(diary)
        } else {
            diaryDao.insert(diary)
        }
    }

}