package com.justin.justinquizapp21.ui.screens.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.justin.justinquizapp21.R
import com.justin.justinquizapp21.databinding.FragmentRegisterBinding
import com.justin.justinquizapp21.ui.screens.register.viewModel.RegisterViewModelImpl
import com.justin.mob21recap.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    override val viewModel: RegisterViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        binding.run {
            btnRegister.setOnClickListener {
                viewModel.register(
                    etName.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etConfirmPassword.text.toString(),
                    role = when (radioGroup.checkedRadioButtonId) {
                        R.id.studentRadioButton -> "Student"
                        R.id.teacherRadioButton -> "Teacher"
                        else -> ""
                    }
                )
            }

            tvLogin.setOnClickListener() {
                navController.popBackStack()
            }
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.success.collect {
                val action = RegisterFragmentDirections.toLogin()
                navController.navigate(action)
            }
        }
    }
}


