package com.lgtm.emoji_diary.di

import android.content.Context
import androidx.room.Room
import com.lgtm.emoji_diary.data.source.DiaryDataSource
import com.lgtm.emoji_diary.data.source.DiaryRepository
import com.lgtm.emoji_diary.data.source.DiaryRepositoryImpl
import com.lgtm.emoji_diary.data.source.local.DiaryDatabase
import com.lgtm.emoji_diary.data.source.local.DiaryLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFooLocalDataSource(
        database: DiaryDatabase,
        ioDispatcher: CoroutineDispatcher
    ): DiaryDataSource {
        return DiaryLocalDataSource(database.fooDao(), ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideFooRepository(
        localTasksDataSource: DiaryDataSource,
        ioDispatcher: CoroutineDispatcher
    ): DiaryRepository {
        return DiaryRepositoryImpl(localTasksDataSource, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): DiaryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            DiaryDatabase::class.java,
            "Foo.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}