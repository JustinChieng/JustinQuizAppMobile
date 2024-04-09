package com.justin.justinquizapp21.ui.screens.questions.viewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.data.model.Question
import com.justin.justinquizapp21.data.model.Score
import com.justin.justinquizapp21.data.model.User
import com.justin.justinquizapp21.data.repo.QuizsRepo
import com.justin.justinquizapp21.data.repo.ScoreRepo
import com.justin.justinquizapp21.data.repo.UserRepo
import com.justin.justinquizapp21.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModelImpl @Inject constructor(
    private val quizsRepo: QuizsRepo,
    private val scoreRepo: ScoreRepo,
    private val savedStateHandle: SavedStateHandle,
    private val authService: AuthService,
    private val userRepo: UserRepo
) : BaseViewModel(), QuestionViewModel {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    override val questions: StateFlow<List<Question>> = _questions
    private val _currentQuestion = MutableStateFlow<Question?>(null)
    override val currentQuestion: StateFlow<Question?> = _currentQuestion
    private val _hasMoreQuestions = MutableStateFlow(true)
    override val hasMoreQuestions: StateFlow<Boolean> = _hasMoreQuestions
    private val _selectedOption = MutableStateFlow<String?>(null)
    override val selectedOption: StateFlow<String?> = _selectedOption
    private val _score = MutableStateFlow(0) // Initialize score to 0
    override val score: StateFlow<Int> = _score
    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown", role = "Unknown"))
    val user: StateFlow<User> = _user



    init {
//        val quizId = savedStateHandle.get<String>("quizId")
//        if (!quizId.isNullOrBlank()) {
//            loadQuestions(quizId)
//        }
        getCurrentUser()
    }

    override fun loadQuestions(quizId: String) {
        viewModelScope.launch {
            // Fetch questions from the repository based on quizId
            val fetchedQuestions = quizsRepo.getQuestionsForQuiz(quizId)
            Log.d("another1", fetchedQuestions.toString())
            _questions.value = fetchedQuestions
            if(fetchedQuestions.isNotEmpty()) {
                _currentQuestion.value = fetchedQuestions[0]
            }
        }
    }

    override fun loadNextQuestion(quizId: String) {
        val currentQuestions = _questions.value
        val currentQuestion = _currentQuestion.value
        val selectedOption = _selectedOption.value
        // Check if the selected option is correct
        val isCorrect = selectedOption == currentQuestion?.correctAnswer

        if (currentQuestions != null && currentQuestion != null && selectedOption != null) {
            val currentIndex = currentQuestions.indexOf(currentQuestion)

            if (currentIndex + 1 < currentQuestions.size) {
                // Load the next question
                _currentQuestion.value = currentQuestions[currentIndex + 1]

                // Check if the selected option is correct
                val isCorrect = selectedOption == currentQuestion.correctAnswer

                // Update the score if the option is correct
                if (isCorrect) {
                    _score.value = _score.value + 1
                }

                // Update the hasMoreQuestions state
                _hasMoreQuestions.value = currentIndex + 1 < currentQuestions.size
            } else {
                // No more questions
                _hasMoreQuestions.value = false

                // Call addResult when the quiz is completed
                val score = _score.value
                val result = Score(name = user.value.name, result = score.toString(), quizId = quizId, )
                viewModelScope.launch {
                    scoreRepo.addResult(result)
                }
            }

            // Clear the selected option for the next question
            _selectedOption.value = null
        }
    }

    override fun getCurrentUser() {
        val firebaseUser = authService.getCurrentUser()
        Log.d("debugging", firebaseUser?.uid.toString())
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler { userRepo.getUser(it.uid) }?.let {  user ->
                    Log.d("debugging", user.toString())
                    _user.value = user
                }
            }
        }
    }


    override fun setSelectedOption(option: String) {
        _selectedOption.value = option
    }
}
