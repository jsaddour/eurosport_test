package com.jsaddour.eurosporttestapp.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jsaddour.domain.models.Sport

@Entity(tableName = "videos")
data class Video(
    @PrimaryKey
    val id: Int,
    val date: Long,
    @Embedded(prefix = "sport_")
    val sport: Sport,
    val title: String,
    val thumb: String,
    val url: String,
    val views: Int
){

}