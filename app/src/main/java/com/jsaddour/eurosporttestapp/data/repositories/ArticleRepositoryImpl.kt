package com.jsaddour.eurosporttestapp.data.repositories

import com.jsaddour.domain.models.Article
import com.jsaddour.eurosporttestapp.data.dao.ArticleDao
import com.jsaddour.eurosporttestapp.services.EurosportClient
import com.jsaddour.domain.repositories.ArticleRepository
import com.jsaddour.eurosporttestapp.services.entities.toDb
import com.jsaddour.eurosporttestapp.services.entities.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


class ArticleRepositoryImpl(
    private val client: EurosportClient,
    private val dao: ArticleDao,
) : ArticleRepository {

    override fun observeArticles(): Flow<Pair<List<Article>, List<Article>>> {
        return dao.observeStories().combine(dao.observeVideos()) { s, v ->
            Pair(s.map { it.toDomain() }, v.map { it.toDomain() })
        }
    }

    override suspend fun getStory(id: Int): Article.Story? {
        return dao.getStoryById(id)?.toDomain()
    }

    override suspend fun refreshArticles(): Boolean {
        return try {
            val articles = client.getArticles()
            dao.insertStories(articles.stories.map { it.toDb() })
            dao.insertVideos(articles.videos.map { it.toDb() })
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}