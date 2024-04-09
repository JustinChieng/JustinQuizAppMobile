package com.justin.justinquizapp21.ui.screens.quiz.viewModel
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justin.justinquizapp21.data.model.Quiz
import com.justin.justinquizapp21.data.repo.QuizsRepo
import com.justin.justinquizapp21.ui.screens.base.viewModel.BaseViewModel
import com.justin.justinquizapp21.ui.screens.quiz.viewModel.QuizHomeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizHomeViewModelImpl @Inject constructor(
    private val quizsRepo: QuizsRepo,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(), QuizHomeViewModel {

    private val _quizTitle = MutableStateFlow("empty")
    override val quizTitle: StateFlow<String> = _quizTitle

    private val _quizDuration = MutableStateFlow(0)
    override val quizDuration: StateFlow<Int> = _quizDuration

    init {
        // Retrieve quizId from SavedStateHandle
        val quizId = savedStateHandle.get<String>("quizId")
        if (!quizId.isNullOrBlank()) {
            setQuizDetails(quizId)
        }
    }

    override fun setQuizDetails(quizId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Use your injected QuizRepo to fetch quiz details using quizId
            val quiz = quizsRepo.getQuiz(quizId)


            Log.d("another", quiz.toString())
            if (quiz != null) {
                // Update StateFlow values
                Log.d("debugging", quiz.title)
                _quizTitle.value = quiz.title
                _quizDuration.value = quiz.duration
            }
        }
    }
}