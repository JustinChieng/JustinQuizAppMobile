package com.justin.justinquizapp21.ui.screens.resultQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.justin.justinquizapp21.databinding.FragmentResultBinding
import com.justin.justinquizapp21.ui.adapter.ScoresAdapter
import com.justin.justinquizapp21.ui.screens.resultQuiz.viewModel.ResultViewModelImpl
import com.justin.mob21recap.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>() {
    override val viewModel: ResultViewModelImpl by viewModels()
    private lateinit var adapter: ScoresAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInflater: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        setupAdapter()

        // Access the quizId passed from previous fragment
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        val quizId = args.quizId
        val score = args.score
        binding.tvScore.text = "Your Score: $score"

        // Fetch scores for the quiz
        viewModel.getScores(quizId)

        binding.fabExit.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToLoginFragment()
            navController.navigate(action)
        }

        binding.fabHome.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToStudentHomeFragment()
            navController.navigate(action)
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.scores.collect {
                adapter.setScores(it)
            }
        }
    }

    private fun setupAdapter() {
        adapter = ScoresAdapter(emptyList())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvScores.adapter = adapter
        binding.rvScores.layoutManager = layoutManager
    }
}
