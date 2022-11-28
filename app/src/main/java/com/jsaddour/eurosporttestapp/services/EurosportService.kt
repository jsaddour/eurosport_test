package com.jsaddour.eurosporttestapp.services

import com.jsaddour.eurosporttestapp.services.entities.ArticlesResponse
import retrofit2.http.GET

interface EurosportService {

    @GET("bin/edfefba")
    suspend fun getArticles(): ArticlesResponse
}