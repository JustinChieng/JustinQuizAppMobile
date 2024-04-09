package com.justin.justinquizapp21.ui.screens.addQuestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.justin.justinquizapp21.R
import com.justin.justinquizapp21.databinding.FragmentAddQuestionBinding
import com.justin.justinquizapp21.ui.screens.addQuestion.viewModel.AddQuestionViewModelImpl
import com.justin.mob21recap.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.justin.justinquizapp21.data.model.Question

@AndroidEntryPoint
class AddQuestionFragment : BaseFragment<FragmentAddQuestionBinding>() {
    override val viewModel: AddQuestionViewModelImpl by viewModels()

    private lateinit var tvQuestionCount: TextView
    private lateinit var etQuestionTitle: TextInputEditText
    private lateinit var etOptionA: TextInputEditText
    private lateinit var etOptionB: TextInputEditText
    private lateinit var etOptionC: TextInputEditText
    private lateinit var etOptionD: TextInputEditText
    private lateinit var etCorrectAnswer: TextInputEditText
    private lateinit var btnAddQuestion: Button
    private lateinit var btnDone: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiComponents(view)
        setupViewModelObserver()
    }

    override fun setupUiComponents(view: View) {
        tvQuestionCount = view.findViewById(R.id.tvQuestionCount)
        etQuestionTitle = view.findViewById(R.id.etQuestionTitle)
        etOptionA = view.findViewById(R.id.etOptionA)
        etOptionB = view.findViewById(R.id.etOptionB)
        etOptionC = view.findViewById(R.id.etOptionC)
        etOptionD = view.findViewById(R.id.etOptionD)
        etCorrectAnswer = view.findViewById(R.id.etCorrectAnswer)
        btnAddQuestion = view.findViewById(R.id.btnAddQuestion)
        btnDone = view.findViewById(R.id.btnDone)

        btnAddQuestion.setOnClickListener {
            addQuestion()
        }

        btnDone.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun addQuestion() {
        val title = etQuestionTitle.text.toString()
        val optionA = etOptionA.text.toString()
        val optionB = etOptionB.text.toString()
        val optionC = etOptionC.text.toString()
        val optionD = etOptionD.text.toString()
        val correctAnswer = etCorrectAnswer.text.toString()

        // Retrieve quizId from arguments
        val quizId = arguments?.getString("quizId") ?: ""

        // Check if the quizId is valid
        if (quizId.isNullOrEmpty()) {
            // Handle the error or throw an exception
            throw IllegalArgumentException("Invalid quizId")
        }

        val question = Question(
            title = title,
            optionA = optionA,
            optionB = optionB,
            optionC = optionC,
            optionD = optionD,
            correctAnswer = correctAnswer
        )

        // Add the question to the ViewModel
        lifecycleScope.launch {
            viewModel.addQuestion(quizId, question)

            // Clear the fields after adding a question
            etQuestionTitle.text?.clear()
            etOptionA.text?.clear()
            etOptionB.text?.clear()
            etOptionC.text?.clear()
            etOptionD.text?.clear()
            etCorrectAnswer.text?.clear()

        }

        viewModel.questionCount.observe(viewLifecycleOwner) { count ->
            tvQuestionCount.text = "Questions added: $count"
        }
    }
}
