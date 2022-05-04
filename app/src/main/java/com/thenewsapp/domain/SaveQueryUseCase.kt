package com.thenewsapp.domain

import com.thenewsapp.data.db.Query
import com.thenewsapp.data.repository.QueryRepository

class SaveQueryUseCase(private val queryRepository: QueryRepository) {

    suspend operator fun invoke(query: Query) {
        queryRepository.insert(query)
    }
}