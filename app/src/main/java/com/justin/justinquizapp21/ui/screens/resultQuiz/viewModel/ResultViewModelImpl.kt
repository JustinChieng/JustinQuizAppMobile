package com.justin.justinquizapp21.ui.screens.resultQuiz.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.justin.justinquizapp21.data.model.Score
import com.justin.justinquizapp21.data.repo.ScoreRepo
import com.justin.justinquizapp21.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModelImpl @Inject constructor(private val scoreRepo: ScoreRepo) :
    BaseViewModel(), ResultViewModel {

    private val _scores = MutableStateFlow<List<Score>>(emptyList())
    override val scores: StateFlow<List<Score>> = _scores

    override fun getScores(quizId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepo.getResult().collect{
                _scores.value = it
            }
        }
    }
}
