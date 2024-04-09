package com.justin.justinquizapp21.ui.screens.questions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.justin.justinquizapp21.data.model.Question
import com.justin.justinquizapp21.databinding.FragmentQuestionBinding
import com.justin.justinquizapp21.ui.screens.questions.viewModel.QuestionViewModelImpl
import com.justin.mob21recap.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionFragment : BaseFragment<FragmentQuestionBinding>() {
    override val viewModel: QuestionViewModelImpl by viewModels()
    private val args: QuestionFragmentArgs by navArgs()
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUiComponents(view)
        setupViewModelObserver()


        // Use the quizId to fetch and display questions
            viewModel.loadQuestions(args.quizId)
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        // Your other UI setup code...

        binding.btnNext.setOnClickListener {
            if (viewModel.hasMoreQuestions.value) {
                // Call the ViewModel method to load the next question
                viewModel.loadNextQuestion(args.quizId)
            } else {
                // Navigate to the ResultFragment when there are no more questions
                val action = QuestionFragmentDirections
                    .actionQuestionFragmentToResultFragment(quizId = args.quizId, viewModel.score.value)
                navController.navigate(action)
            }
        }

    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

        lifecycleScope.launch {
            viewModel.currentQuestion.collect { question ->
                // Update your UI with the current question
                if (question != null) {
                    updateQuestionViews(question)
                }
            }
        }
    }

    private fun updateQuestionViews(question: Question) {
        binding.tvQuestion.text = question.title

        // Clear existing radio button selections
        binding.radioGroup.clearCheck()

        // Set radio button texts based on options
        setRadioButtonText(binding.radioOp1, question.optionA)
        setRadioButtonText(binding.radioOp2, question.optionB)
        setRadioButtonText(binding.radioOp3, question.optionC)
        setRadioButtonText(binding.radioOp4, question.optionD)

        // Check the previously selected option, if any
        val selectedOption = viewModel.selectedOption.value
        when (selectedOption) {
            binding.radioOp1.text.toString() -> binding.radioOp1.isChecked = true
            binding.radioOp2.text.toString() -> binding.radioOp2.isChecked = true
            binding.radioOp3.text.toString() -> binding.radioOp3.isChecked = true
            binding.radioOp4.text.toString() -> binding.radioOp4.isChecked = true
        }

        // Set up a listener for radio button changes
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Get the text of the selected radio button
            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton?.text.toString()

            // Update the selected option in the ViewModel
            viewModel.setSelectedOption(selectedText)
        }
    }


    private fun setRadioButtonText(radioButton: RadioButton, text: String) {
        radioButton.text = text
    }
}
