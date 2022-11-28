package com.jsaddour.eurosporttestapp.di

import android.content.Context
import androidx.room.Room
import com.jsaddour.domain.repositories.ArticleRepository
import com.jsaddour.eurosporttestapp.data.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.jsaddour.eurosporttestapp.data.dao.ArticleDao
import com.jsaddour.eurosporttestapp.data.repositories.ArticleRepositoryImpl
import com.jsaddour.eurosporttestapp.services.EurosportClient

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext,
        Database::class.java,
        "eurosportDataBase"
    ).build()

    @Singleton
    @Provides
    fun provideStoryArticleDao(eurosportDataBase: Database) = eurosportDataBase.articleDao()


    @Provides
    fun provideArticleRepository(
        client: EurosportClient,
        dao: ArticleDao
    ): ArticleRepository = ArticleRepositoryImpl(
        client = client,
        dao = dao
    )
}