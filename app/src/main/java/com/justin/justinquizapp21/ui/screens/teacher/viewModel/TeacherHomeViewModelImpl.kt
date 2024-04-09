package com.justin.justinquizapp21.ui.screens.teacher.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justin.justinquizapp21.data.model.Quiz
import com.justin.justinquizapp21.data.repo.QuizsRepo
import com.justin.justinquizapp21.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherHomeViewModelImpl @Inject constructor(
    private val quizsRepo: QuizsRepo
): BaseViewModel(), TeacherHomeViewModel {
    private val _quizs = MutableStateFlow<List<Quiz>>(
        (1..2).map {
            Quiz(title = "Title $it", quizid = "Quiz_id $it", duration = it, questionCount = it)
        }.toList()
    )
    override val quizs: StateFlow<List<Quiz>> = _quizs

    init {
        getQuizs()

        viewModelScope.launch {
            quizsRepo.getQuiz("qaz")?.let {
                Log.d("debugging", it.toString())
            }
        }
    }

    override fun getQuizs() {
        viewModelScope.launch(Dispatchers.IO){
            quizsRepo.getQuizs().collect {
                Log.d("debugging",it.toString())
                _quizs.value = it
            }
        }
    }

    override fun delete(quiz: Quiz) {
        viewModelScope.launch(Dispatchers.IO) {
            quizsRepo.deleteQuiz(quiz.id.toString())
        }
    }
}