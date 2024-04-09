package com.justin.justinquizapp21.ui.screens.login.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.core.service.AuthServiceImpl
import com.justin.justinquizapp21.data.model.User
import com.justin.justinquizapp21.data.repo.UserRepo
import com.justin.justinquizapp21.ui.screens.base.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor (
    private val authService: AuthService,
    private val userRepo: UserRepo
) : BaseViewModel(), LoginViewModel {

    private val _user = MutableStateFlow(User(name = "unknown", email = "unknown", role = "unknown"))
    val user : StateFlow<User> = _user

    override fun login(email: String, pass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            //new
            val result = errorHandler {
                authService.login(email, pass)
            }
            if (result != null) {
                _success.emit("Login Successful")
            }
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
}