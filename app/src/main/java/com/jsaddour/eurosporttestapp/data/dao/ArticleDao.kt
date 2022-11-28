package com.jsaddour.eurosporttestapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jsaddour.eurosporttestapp.data.entities.Story
import com.jsaddour.eurosporttestapp.data.entities.Video
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStories(stories: List<Story>)

    @Query("SELECT * FROM stories ORDER BY date DESC")
    fun observeStories(): Flow<List<Story>>

    @Query("SELECT * FROM stories WHERE stories.id = :id")
    suspend fun getStoryById(id: Int): Story?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(storyNewsData: List<Video>)

    @Query("SELECT * FROM videos ORDER BY date DESC")
    fun observeVideos(): Flow<List<Video>>

    @Query("SELECT * FROM videos WHERE videos.id = :id")
    suspend fun getVideoById(id: Int): Video?

}