package com.justin.justinquizapp21.ui.screens.register.viewModel

import androidx.lifecycle.viewModelScope
import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.core.service.AuthServiceImpl
import com.justin.justinquizapp21.data.model.User
import com.justin.justinquizapp21.data.repo.UserRepo
import com.justin.justinquizapp21.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel(), RegisterViewModel {
    override fun register(name: String, email: String, pass: String, confirmPass: String, role: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val user = errorHandler {
                authService.register(email, pass)
            }

            if (user != null) {
                _success.emit("Register successfully")
                errorHandler {
                    userRepo.addUser(
                        User(name = name, email = email, role = role)
                    )
                }
            }
        }
    }
}

