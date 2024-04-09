package com.justin.justinquizapp21.ui.screens.quiz.viewModel
import kotlinx.coroutines.flow.StateFlow

interface QuizHomeViewModel {
    val quizTitle: StateFlow<String>
    val quizDuration: StateFlow<Int>

    fun setQuizDetails(quizId: String)
}
