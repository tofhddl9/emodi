package com.lgtm.emodi.di

import android.content.Context
import androidx.room.Room
import com.lgtm.emodi.data.source.DiaryDataSource
import com.lgtm.emodi.data.source.DiaryRepository
import com.lgtm.emodi.data.source.DiaryRepositoryImpl
import com.lgtm.emodi.data.source.local.DiaryDatabase
import com.lgtm.emodi.data.source.local.DiaryLocalDataSource
import com.lgtm.emodi.view.edit.validate.ValidateContent
import com.lgtm.emodi.view.edit.validate.ValidateEmoji
import com.lgtm.emodi.view.edit.validate.ValidateTitle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
    fun provideDiaryLocalDataSource(
        database: DiaryDatabase,
        ioDispatcher: CoroutineDispatcher
    ): DiaryDataSource {
        return DiaryLocalDataSource(database.fooDao(), ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideDiaryRepository(
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
            "Diary.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Module
@InstallIn(ViewModelComponent::class)
object EditViewModelModule {

    @Provides
    fun provideValidateTitle(): ValidateTitle = ValidateTitle()

    @Provides
    fun provideValidateContent(): ValidateContent = ValidateContent()

    @Provides
    fun provideValidateEmoji(): ValidateEmoji = ValidateEmoji()

}