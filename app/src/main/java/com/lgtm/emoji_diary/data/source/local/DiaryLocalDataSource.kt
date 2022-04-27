package com.lgtm.emoji_diary.data.source.local

import com.lgtm.emoji_diary.data.source.DiaryDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DiaryLocalDataSource(
    private val fooDao: DiaryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DiaryDataSource {
}