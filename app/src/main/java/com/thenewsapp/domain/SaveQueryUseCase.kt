package com.thenewsapp.domain

import com.thenewsapp.data.db.Query
import com.thenewsapp.data.repository.QueryRepository
import javax.inject.Inject

class SaveQueryUseCase @Inject constructor(private val queryRepository: QueryRepository) {

    suspend operator fun invoke(query: Query) {
        queryRepository.insert(query)
    }
}