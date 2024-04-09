package com.justin.justinquizapp21.ui.screens.teacher.viewModel

import com.justin.justinquizapp21.data.model.Quiz
import kotlinx.coroutines.flow.StateFlow

interface TeacherHomeViewModel {
    val quizs: StateFlow<List<Quiz>>

    fun getQuizs()
    fun delete(quiz: Quiz)
}