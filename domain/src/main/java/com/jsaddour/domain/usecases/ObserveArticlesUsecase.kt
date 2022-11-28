package com.jsaddour.domain.usecases

import com.jsaddour.domain.models.Article
import com.jsaddour.domain.repositories.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ObserveArticlesUsecase @Inject constructor(
    private val repository: ArticleRepository
) {

    fun execute(): Flow<List<Article>> = repository.observeArticles().map { (stories, videos) ->
        when {
            stories.isEmpty() && videos.isEmpty() -> emptyList()
            stories.isNotEmpty() && videos.isEmpty() -> stories
            stories.isEmpty() && videos.isNotEmpty() -> videos
            else -> {
                if(stories.first().date.after(videos.first().date) ||
                    stories.first().date == videos.first().date){
                    merge(stories, videos)
                } else {
                    merge(videos, stories)
                }
            }
        }
    }

    fun merge(firstList: List<Article>, secondList: List<Article>): List<Article> {
        firstList.zip(secondList).flatMap { listOf(it.first, it.second) }.apply {
           val remainingItems =  if (firstList.size > secondList.size) firstList.drop(secondList.size) else secondList.drop(firstList.size)
            return this + remainingItems
        }
    }

}