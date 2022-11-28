package com.jsaddour.domain.repositories

import com.jsaddour.domain.models.Article
import kotlinx.coroutines.flow.Flow


interface ArticleRepository {

    fun observeArticles(): Flow<Pair<List<Article>, List<Article>>>
    suspend fun refreshArticles(): Boolean
    suspend fun getStory(id: Int): Article.Story?
}