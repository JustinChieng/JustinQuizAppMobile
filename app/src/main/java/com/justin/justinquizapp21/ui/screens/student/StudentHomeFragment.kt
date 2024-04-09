package com.justin.justinquizapp21.ui.screens.student

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.justin.justinquizapp21.R
import com.justin.justinquizapp21.databinding.FragmentStudentHomeBinding
import com.justin.justinquizapp21.ui.adapter.QuizsAdapter
import com.justin.justinquizapp21.ui.screens.login.LoginFragmentDirections
import com.justin.justinquizapp21.ui.screens.student.viewModel.StudentHomeViewModelImpl
import com.justin.mob21recap.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentHomeFragment : BaseFragment<FragmentStudentHomeBinding>() {
    override val viewModel: StudentHomeViewModelImpl by viewModels()
    private lateinit var adapter: QuizsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentHomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        binding.fabExit.setOnClickListener{
            val action = StudentHomeFragmentDirections.actionStudenthomeToLogin()
            navController.navigate(action)
        }

        binding.btnGo.setOnClickListener {
            val quizId = binding.etQuizID.text.toString()
            val action = StudentHomeFragmentDirections
                .actionStudentHomeFragmentToQuizHomeFragment(quizId)
            navController.navigate(action)
        }


    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()

    }


}