package com.jsaddour.eurosporttestapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jsaddour.eurosporttestapp.data.dao.ArticleDao
import com.jsaddour.eurosporttestapp.data.entities.Story
import com.jsaddour.eurosporttestapp.data.entities.Video


@Database(
    entities = [
        Story::class,
        Video::class,
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}