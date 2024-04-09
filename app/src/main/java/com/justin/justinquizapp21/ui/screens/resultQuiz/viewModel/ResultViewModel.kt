package com.justin.justinquizapp21.ui.screens.resultQuiz.viewModel

import com.justin.justinquizapp21.data.model.Score
import kotlinx.coroutines.flow.StateFlow

interface ResultViewModel {
    val scores: StateFlow<List<Score>>

   fun getScores(quizId: String)
}
