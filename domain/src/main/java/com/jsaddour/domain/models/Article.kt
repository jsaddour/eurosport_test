package com.jsaddour.domain.models

import java.util.*


sealed interface Article {
    val id: Int
    val date: Date
    val sport: Sport
    val title: String

    data class Story(
        override val id: Int,
        override val date: Date,
        override val sport: Sport,
        override val title: String,
        val author: String,
        val image: String,
        val teaser: String
    ) : Article

    data class Video(
        override val id: Int,
        override val date: Date,
        override val sport: Sport,
        override val title: String,
        val thumb: String,
        val url: String,
        val views: Int
    ) : Article
}
