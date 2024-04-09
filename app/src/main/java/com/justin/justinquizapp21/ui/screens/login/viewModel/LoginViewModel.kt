package com.justin.justinquizapp21.ui.screens.login.viewModel

interface LoginViewModel {
    fun login (email: String, pass: String)
    fun getCurrentUser()
}