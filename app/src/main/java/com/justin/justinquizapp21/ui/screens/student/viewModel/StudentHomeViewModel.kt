package com.justin.justinquizapp21.ui.screens.student.viewModel

import com.justin.justinquizapp21.data.model.Quiz
import com.justin.justinquizapp21.data.model.User
import kotlinx.coroutines.flow.StateFlow

interface StudentHomeViewModel {
    fun setCurrentUser(user: User)
    fun getCurrentUser(): User?
    val studentName: StateFlow<String?>



}