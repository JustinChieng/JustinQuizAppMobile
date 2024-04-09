package com.justin.justinquizapp21.ui.screens.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.justin.justinquizapp21.R
import com.justin.justinquizapp21.databinding.FragmentLoginBinding
import com.justin.justinquizapp21.ui.screens.login.viewModel.LoginViewModelImpl
import com.justin.mob21recap.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val viewModel: LoginViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)


        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPass.text.toString()
            //viewModel.login("student4@gmail.com", "qweqweqwe")
            //viewModel.login("teacher1@gmail.com", "qweqweqwe")
            viewModel.login(email, pass)
        }

        binding.tvRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginToRegister()
            navController.navigate(action)
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.user.collect {
                val action = when (it.role) {
                    "Student" -> LoginFragmentDirections.toStudentHome()
                    "Teacher" -> LoginFragmentDirections.toTeacherHome()
                    else -> null
                }
                action?.let {navController.navigate(action)}
            }
        }

        lifecycleScope.launch {
            viewModel.success.collect {
                viewModel.getCurrentUser()

            }
        }
    }

}