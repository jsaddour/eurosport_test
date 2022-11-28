package com.jsaddour.eurosporttestapp.services

import javax.inject.Inject


class EurosportClient @Inject constructor(
    private val eurosportService: EurosportService
) {

    suspend fun getArticles() = eurosportService.getArticles()
}