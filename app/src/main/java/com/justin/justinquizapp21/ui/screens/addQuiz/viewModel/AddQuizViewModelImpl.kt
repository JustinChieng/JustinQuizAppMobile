package com.justin.justinquizapp21.ui.screens.addQuiz.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.justin.justinquizapp21.data.model.Question
import com.justin.justinquizapp21.data.model.Quiz
import com.justin.justinquizapp21.data.repo.QuizsRepo
import com.justin.justinquizapp21.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuizViewModelImpl @Inject constructor (
    private val quizsRepo: QuizsRepo

): BaseViewModel(), AddQuizViewModel {
    private val _questionCount = MutableLiveData<Int>()
    val questionCount: LiveData<Int> get() = _questionCount
    private val _done = MutableSharedFlow<Unit>()
    override val done : SharedFlow<Unit> = _done

    override fun addQuiz(title: String, quizid: String, duration: Int, questionCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                quizsRepo.addQuiz(
                    Quiz(title = title, quizid = quizid, duration = duration, questionCount = questionCount)
                )
            }
            _done.emit(Unit)
        }
    }

    override suspend fun addQuestionToQuiz(quizId: String, question: Question) {
        quizsRepo.addQuestionToQuiz(quizId, question)
    }

    fun getQuestionCount(): Int {
        return _questionCount.value ?: 0
    }



}