package com.justin.justinquizapp21.data.repo

import com.justin.justinquizapp21.data.model.Score
import kotlinx.coroutines.flow.Flow

interface ScoreRepo {
    suspend fun addResult(score: Score)
    suspend fun getResult() : Flow<List<Score>>
}