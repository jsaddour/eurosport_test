package com.jsaddour.eurosporttestapp.features.Articles.details.story

import com.jsaddour.domain.models.Article
import com.jsaddour.eurosporttestapp.features.Articles.list.formatTime

data class ViewState(
    val story: StoryItem?,
) {
    data class StoryItem(
        val date: String,
        val sport: String,
        val title: String,
        val author: String,
        val teaser: String,
        val image: String,
    )
}

fun Article.Story.toStoryItem() = ViewState.StoryItem(
    date = date.formatTime(),
    sport = sport.name,
    title = title,
    author = author,
    image = image,
    teaser = teaser
)

