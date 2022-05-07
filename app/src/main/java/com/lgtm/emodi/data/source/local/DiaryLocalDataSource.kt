package com.lgtm.emodi.data.source.local

import com.lgtm.emodi.data.Diary
import com.lgtm.emodi.data.source.DiaryDataSource
import com.lgtm.emodi.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DiaryLocalDataSource(
    private val diaryDao: DiaryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DiaryDataSource {

    override fun getDiaries(count: Int): Flow<Result<List<Diary>>> {
        return diaryDao.getDiaries(count).map {
            try {
                Result.Success(it)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getDiary(diaryId: Long): Result<Diary> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(diaryDao.getDiary(diaryId))
        } catch(e: Exception) {
            Result.Error(e)
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