package com.justin.justinquizapp21.ui.screens.quiz


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.justin.justinquizapp21.R
import com.justin.justinquizapp21.databinding.FragmentQuizHomeBinding
import com.justin.justinquizapp21.databinding.FragmentStudentHomeBinding
import com.justin.justinquizapp21.ui.adapter.QuizsAdapter
import com.justin.justinquizapp21.ui.screens.quiz.viewModel.QuizHomeViewModelImpl

import com.justin.justinquizapp21.ui.screens.student.viewModel.StudentHomeViewModelImpl
import com.justin.mob21recap.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizHomeFragment : BaseFragment<FragmentQuizHomeBinding>() {
    override val viewModel: QuizHomeViewModelImpl by viewModels()
    private lateinit var quizId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizHomeBinding.inflate(inflater, container, false)
        quizId = arguments?.getString("quizId") ?: ""
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {
            viewModel.quizDuration.collect{
                binding.tvTimer.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.quizTitle.collect {
                binding.tvQuizTitle.text = it
            }
        }

        binding.btnStart.setOnClickListener {
            val action = QuizHomeFragmentDirections.actionQuizHomeFragmentToQuestionFragment(quizId)
            navController.navigate(action)
        }

    }



}