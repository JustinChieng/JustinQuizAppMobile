package com.justin.justinquizapp21.ui.screens.register.viewModel

interface RegisterViewModel {
    fun register(name: String, email: String, pass: String, confirmPass:String, role: String)
}