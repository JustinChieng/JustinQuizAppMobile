package com.justin.justinquizapp21.ui.screens.addQuestion.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justin.justinquizapp21.data.model.Question
import com.justin.justinquizapp21.data.repo.QuizsRepo
import com.justin.justinquizapp21.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuestionViewModelImpl @Inject constructor(
    private val quizsRepo: QuizsRepo,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(), AddQuestionViewModel {

    private val _questionCount = MutableLiveData<Int>()
    val questionCount: LiveData<Int> get() = _questionCount



    override fun addQuestion(quizId: String,question: Question) {

        // Retrieve quizId from savedStateHandle
        val quizId: String = savedStateHandle.get("quizId") ?: ""

        if (quizId.isNullOrEmpty()) {
            // Handle the error or throw an exception
            throw IllegalArgumentException("Invalid quizId")
        }

        viewModelScope.launch {
            // Add the question to the repository
            val addedQuestionId = quizsRepo.addQuestionToQuiz(quizId, question)

            // Update the question's ID after it's added to Firestore
            question.id = addedQuestionId.toString()

            // Query the repository for the total question count
            val totalCount = quizsRepo.getQuestionCountForQuiz(quizId)

            // Now you can use `totalCount` as needed, for example, update UI
            _questionCount.value = totalCount
        }
    }
}
