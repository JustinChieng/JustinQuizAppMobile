package com.justin.justinquizapp21.ui.screens.student.viewModel

import androidx.lifecycle.viewModelScope
import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.data.model.Quiz
import com.justin.justinquizapp21.data.model.User
import com.justin.justinquizapp21.data.repo.UserRepo
import com.justin.justinquizapp21.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModelImpl @Inject constructor(
    private val userRepo: UserRepo,
    private val authService: AuthService
) : BaseViewModel(), StudentHomeViewModel {

    private val currentUser: MutableStateFlow<User?> = MutableStateFlow(null)
    private val _studentName = MutableStateFlow<String?>(null)

    override fun setCurrentUser(user: User) {
        currentUser.value = user
        _studentName.value = user.name
    }

    override fun getCurrentUser(): User? {
        return currentUser.value
    }

    override val studentName: StateFlow<String?>
        get() = _studentName


}