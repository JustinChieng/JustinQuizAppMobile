package com.justin.justinquizapp21.ui.screens.addQuestion.viewModel

import androidx.lifecycle.LiveData
import com.justin.justinquizapp21.data.model.Question

interface AddQuestionViewModel {

    fun addQuestion(quizId: String, question: Question)
}
