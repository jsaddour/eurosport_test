package com.jsaddour.eurosporttestapp.services.entities

import com.jsaddour.domain.models.Sport
import com.jsaddour.eurosporttestapp.services.entities.ArticlesResponse.Story as StoryResponse
import com.jsaddour.eurosporttestapp.services.entities.ArticlesResponse.Video as VideoResponse
import com.jsaddour.eurosporttestapp.data.entities.Story as StoryDB
import com.jsaddour.eurosporttestapp.data.entities.Video as VideoDB
import com.jsaddour.domain.models.Article
import java.util.*



fun StoryResponse.toDb() = StoryDB(
    id = id,
    date = date.round(),
    sport = Sport(
        id = sport.id,
        name = sport.name,
    ),
    title = title,
    author = author,
    image = image,
    teaser = teaser
)

fun VideoResponse.toDb() = VideoDB(
    id = id,
    date = date.round(),
    sport = Sport(
        id = sport.id,
        name = sport.name,
    ),
    title = title,
    thumb = thumb,
    url = url,
    views = views
)

fun StoryDB.toDomain() = Article.Story(
    id = id,
    date = Date(date),
    sport = sport,
    title = title,
    author = author,
    image = image,
    teaser = teaser
)

fun VideoDB.toDomain() = Article.Video(
    id = id,
    date = Date(date),
    sport = sport,
    title = title,
    thumb = thumb,
    url = url,
    views = views
)

private fun Double.round() = (this * 1000).toLong()
