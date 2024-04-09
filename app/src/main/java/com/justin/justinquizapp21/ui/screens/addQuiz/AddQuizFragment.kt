package com.justin.justinquizapp21.ui.screens.addQuiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.justin.justinquizapp21.databinding.FragmentAddQuizBinding
import com.justin.justinquizapp21.ui.screens.addQuiz.viewModel.AddQuizViewModelImpl
import com.justin.mob21recap.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddQuizFragment : BaseFragment<FragmentAddQuizBinding>() {
    override val viewModel: AddQuizViewModelImpl by viewModels()
    private val seekBar: SeekBar by lazy { binding.sbCountDown }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        binding.run {

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, duration: Int, fromUser: Boolean) {
                    tvCountDown.text = duration.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            btnDone.setOnClickListener {
                viewModel.addQuiz(
                    tvTitle.text.toString(),
                    tvQuizID.text.toString(),
                    seekBar.progress,
                    viewModel.getQuestionCount()
                )
            }


            fabBack.setOnClickListener{
                val action = AddQuizFragmentDirections.actionAddQuizFragmentToTeacherHomeFragment()
                navController.navigate(action)
            }

            btnAddQuestion.setOnClickListener {
                val currentQuizId = tvQuizID.text.toString()
                val action = AddQuizFragmentDirections.actionAddQuizFragmentToAddQuestionFragment(quizId = currentQuizId)
                navController.navigate(action)
            }




        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.done.collect {
                navController.popBackStack()
                updateViewsAfterQuestionsAdded()
            }
        }
    }

     private fun updateViewsAfterQuestionsAdded() {
        binding.btnAddQuestion.visibility = View.INVISIBLE
        binding.tvQuizAdded.visibility = View.VISIBLE
    }




}