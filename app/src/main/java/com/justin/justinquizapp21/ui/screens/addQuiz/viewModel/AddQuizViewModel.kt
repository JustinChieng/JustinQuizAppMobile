package com.justin.justinquizapp21.ui.screens.addQuiz.viewModel

import com.justin.justinquizapp21.data.model.Question
import kotlinx.coroutines.flow.SharedFlow

interface AddQuizViewModel {
    val done: SharedFlow<Unit>
    fun addQuiz (title: String, quizid:String, duration: Int, questionCount: Int)
    suspend fun addQuestionToQuiz(quizId: String, question: Question)

}