package com.justin.justinquizapp21.ui.screens.questions.viewModel

import kotlinx.coroutines.flow.StateFlow
import com.justin.justinquizapp21.data.model.Question
import kotlinx.coroutines.flow.MutableStateFlow

interface QuestionViewModel {
    val questions: StateFlow<List<Question>>
    val currentQuestion: StateFlow<Question?>
    val hasMoreQuestions: StateFlow<Boolean>
    val selectedOption: StateFlow<String?>
    val score: StateFlow<Int>

    fun getCurrentUser()
    fun loadQuestions(quizId: String)
    fun loadNextQuestion(quizId: String)
    fun setSelectedOption(option: String)
}
