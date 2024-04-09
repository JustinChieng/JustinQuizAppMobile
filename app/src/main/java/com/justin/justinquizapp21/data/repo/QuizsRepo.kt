package com.justin.justinquizapp21.data.repo

import com.justin.justinquizapp21.data.model.Question
import com.justin.justinquizapp21.data.model.Quiz
import com.justin.justinquizapp21.data.model.Score
import kotlinx.coroutines.flow.Flow

interface QuizsRepo {
    suspend fun getQuizs(): Flow<List<Quiz>>
    suspend fun addQuiz(quiz: Quiz)
    suspend fun getQuiz(id: String): Quiz?
    suspend fun deleteQuiz(id: String)



    suspend fun getQuestionsForQuiz(quizId: String): List<Question>
    suspend fun getQuestionById(questionId: String): Question?


    suspend fun addQuestionToQuiz(quizId: String, question: Question): String
    suspend fun getQuestionCountForQuiz(quizId: String): Int

}


