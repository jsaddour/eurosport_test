package com.jsaddour.eurosporttestapp.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jsaddour.domain.models.Sport


@Entity(tableName = "stories")
data class Story(
    @PrimaryKey
    val id: Int,
    val date: Long,
    @Embedded(prefix = "sport_")
    val sport: Sport,
    val title: String,
    val author: String,
    val image: String,
    val teaser: String
)