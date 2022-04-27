package com.lgtm.emoji_diary.data.source

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DiaryRepositoryImpl(
    private val fooLocalDataSource: DiaryDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DiaryRepository {

}