package com.jsaddour.eurosporttestapp.features.Articles.list

import android.text.format.DateUtils
import com.jsaddour.domain.models.Article
import java.util.*

data class ViewState(
    val items: List<Item>,
    val loading: Boolean,
    val error: Item.Event<String>?,
    val refresh: () -> Unit
) {
    companion object {
        fun firstState(refresh: () -> Unit): ViewState =
            ViewState(
                emptyList(),
                true,
                null,
                refresh
            )
    }
}

fun Date.formatTime(): String = DateUtils.getRelativeTimeSpanString(
    getTime(),
    Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS
).toString()

private fun Article.toItem(): Item {
    return when (this) {
        is Article.Story -> Item.Story(
            id = id,
            date = date.formatTime(),
            sport = sport.name,
            title = title,
            author = author,
            image = image
        )
        is Article.Video -> Item.Video(
            id = id,
            sport = sport.name,
            title = title,
            thumb = thumb,
            url = url,
            views = views.toString()
        )
    }
}

fun ViewState.loading(): ViewState = copy(loading = true, error = null)
fun ViewState.update(articles: List<Article>): ViewState =
    copy(articles.map { it.toItem() }, false, null)

fun ViewState.error(newError: String): ViewState =
    copy(loading = false, error = Item.Event(newError))

sealed interface Item {

    data class Story(
        val id: Int,
        val date: String,
        val sport: String,
        val title: String,
        val author: String,
        val image: String,
    ) : Item

    data class Video(
        val id: Int,
        val sport: String,
        val title: String,
        val thumb: String,
        val url: String,
        val views: String
    ) : Item

    class Event<out T>(private val content: T) {
        private var consumed = false


        fun getContent(): T? {
            return if (consumed) {
                null
            } else {
                consumed = true
                content
            }
        }
    }
}

