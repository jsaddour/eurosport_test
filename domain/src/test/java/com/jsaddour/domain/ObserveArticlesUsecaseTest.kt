package com.jsaddour.domain

import com.jsaddour.domain.models.Article.Story
import com.jsaddour.domain.models.Article.Video
import com.jsaddour.domain.models.Sport
import com.jsaddour.domain.repositories.ArticleRepository
import com.jsaddour.domain.usecases.ObserveArticlesUsecase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

class ObserveArticlesUsecaseTest {
    private val repository = mock<ArticleRepository>()
    private val usecase = ObserveArticlesUsecase(repository)

    private val initialStory = Story(
        id = 0,
        date = Date(),
        sport = Sport(id = 0, name = ""),
        title = "",
        author = "",
        image = "",
        teaser = ""
    )

    private val initialVideo = Video(
        id = 0,
        date = Date(),
        sport = Sport(id = 0, name = ""),
        title = "",
        thumb = "",
        url = "",
        views = 0
    )

    fun Story.incremente(inc : Int) : Story {
       return copy(id = id + inc, date = Date.from(date.toInstant().plusSeconds(inc.toLong()) ) )
    }

    fun Video.incremente(inc : Int) : Video {
      return copy(id = id + inc, date =  Date.from(date.toInstant().plusSeconds(inc.toLong()) ) )
    }

    @Test
    fun `should return empty List`() = runBlocking {
        whenever(repository.observeArticles()).thenReturn(
            flowOf(Pair(emptyList(), emptyList()))
        )

        val articles = usecase.execute().toList().first()
        assertTrue(articles.isEmpty())
    }

    @Test
    fun `should start with story`() = runBlocking {

        val videos = listOf(
            initialVideo.incremente(1),
            initialVideo.incremente(2),
        )

        val stories = listOf(
            initialStory.incremente(3),
            initialStory.incremente(4),
            initialStory.incremente(5),
        )



        whenever(repository.observeArticles()).thenReturn(
            flowOf(Pair(stories, videos))
        )

        val articles = usecase.execute().toList().first().first()
        assertThat(articles, Is(instanceOf(Story::class.java)))
    }

    @Test
    fun `should start with video`() = runBlocking {

        val videos = listOf(
            initialVideo.incremente(3),
            initialVideo.incremente(4),
            initialVideo.incremente(5),
        )

        val stories = listOf(
            initialStory,
            initialStory.incremente(1),
            initialStory.incremente(2),
        )



        whenever(repository.observeArticles()).thenReturn(
            flowOf(Pair(stories, videos))
        )

        val articles = usecase.execute().toList().first().first()
        assertThat(articles, Is(instanceOf(Video::class.java)))
    }

    @Test
    fun `should alternate stories and videos`() = runBlocking {

        val videos = listOf(
            initialVideo.incremente(3),
            initialVideo.incremente(4),
            initialVideo.incremente(5),
        )

        val stories = listOf(
            initialStory,
            initialStory.incremente(1),
            initialStory.incremente(2),
        )

        whenever(repository.observeArticles()).thenReturn(
            flowOf(Pair(stories, videos))
        )

        val articles = usecase.execute().toList().first()
        assertThat(articles.get(0), Is(instanceOf(Video::class.java)))
        assertThat(articles.get(1), Is(instanceOf(Story::class.java)))
        assertThat(articles.get(2), Is(instanceOf(Video::class.java)))
        assertThat(articles.get(3), Is(instanceOf(Story::class.java)))
        assertThat(articles.get(4), Is(instanceOf(Video::class.java)))
        assertThat(articles.get(5), Is(instanceOf(Story::class.java)))
    }
}