package com.jsaddour.domain.usecases


import com.jsaddour.domain.repositories.ArticleRepository
import javax.inject.Inject

class RefreshArticlesUsecase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend fun execute() = repository.refreshArticles()
}