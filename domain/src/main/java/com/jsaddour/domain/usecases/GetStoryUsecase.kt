package com.jsaddour.domain.usecases

import com.jsaddour.domain.repositories.ArticleRepository
import javax.inject.Inject

class GetStoryUsecase @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend fun execute(id: Int) = repository.getStory(id)
}