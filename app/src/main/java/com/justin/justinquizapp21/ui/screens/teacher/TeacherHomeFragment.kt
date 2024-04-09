package com.justin.justinquizapp21.ui.screens.teacher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.justin.justinquizapp21.R
import com.justin.justinquizapp21.data.model.Quiz
import com.justin.justinquizapp21.databinding.FragmentTeacherHomeBinding
import com.justin.justinquizapp21.ui.adapter.QuizsAdapter
import com.justin.justinquizapp21.ui.screens.teacher.viewModel.TeacherHomeViewModelImpl
import com.justin.mob21recap.ui.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class TeacherHomeFragment : BaseFragment<FragmentTeacherHomeBinding>() {
    override val viewModel: TeacherHomeViewModelImpl by viewModels()
    private lateinit var adapter: QuizsAdapter

    private val handler = Handler(Looper.getMainLooper())
    private val updateIntervalMillis = 1000 // Update every 1000 milliseconds (1 second)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInflater: Bundle?
    ): View? {
        binding = FragmentTeacherHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        setupAdapter()
        setCurrentDateAndTime()

        binding.fabExit.setOnClickListener {
            val action = TeacherHomeFragmentDirections.actionTeacherHomeFragmentToLoginFragment()
            navController.navigate(action)
        }

        binding.fabCreateQuiz.setOnClickListener {

            val action = TeacherHomeFragmentDirections.actionTeacherHomeFragmentToAddQuizFragment(quizId = null.toString())
            navController.navigate(action)
        }
    }



    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.quizs.collect{
                adapter.setQuizs(it)
            }
        }
    }

    private fun setupAdapter(){
        adapter = QuizsAdapter(emptyList())
        adapter.listener = object: QuizsAdapter.Listener {
            override fun onDelete(quiz: Quiz) {
                viewModel.delete(quiz)
            }

        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvQuizs.adapter = adapter
        binding.rvQuizs.layoutManager = layoutManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handler.postDelayed(object : Runnable {
            override fun run() {
                setCurrentDateAndTime()
                handler.postDelayed(this, updateIntervalMillis.toLong())
            }
        }, updateIntervalMillis.toLong())
    }

    private fun setCurrentDateAndTime() {

        val tvDateAndTime = binding.root.findViewById<TextView>(R.id.tvDateAndTimeTextView)
        val currentDateTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = dateFormat.format(currentDateTime)
        tvDateAndTime.text = formattedDateTime
    }



}