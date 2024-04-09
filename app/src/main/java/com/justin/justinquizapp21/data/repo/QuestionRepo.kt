package com.justin.justinquizapp21.data.repo

import com.justin.justinquizapp21.data.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepo {
    suspend fun getQuestions(): Flow<List<Question>>
    suspend fun addQuestion(question: Question)
    suspend fun getQuestion(id: String): Question?
    suspend fun deleteQuestion(id: String)
}