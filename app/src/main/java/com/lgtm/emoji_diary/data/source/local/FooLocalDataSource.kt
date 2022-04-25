package com.lgtm.emoji_diary.data.source.local

import com.lgtm.emoji_diary.data.source.FooDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class FooLocalDataSource(
    private val fooDao: FooDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : FooDataSource {
}