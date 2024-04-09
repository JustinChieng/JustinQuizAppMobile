package com.justin.justinquizapp21.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justin.justinquizapp21.data.model.Quiz
import com.justin.justinquizapp21.databinding.ItemLayoutQuizBinding

class QuizsAdapter(
    private var quizs: List<Quiz>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var listener: Listener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = ItemLayoutQuizBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuizItemViewHolder(binding)
    }

    override fun getItemCount() = quizs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quiz = quizs[position]
        if (holder is QuizItemViewHolder) {
            holder.bind(quiz)
        }
    }

    fun setQuizs(quizs: List<Quiz>) {
        this.quizs = quizs
        notifyDataSetChanged()
    }

    inner class QuizItemViewHolder(
        private val binding: ItemLayoutQuizBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.run {
                tvQuizTitle.text = "Title: ${quiz.title}"
                tvQuizID.text = "QuizId: ${quiz.quizid}"
                tvDuration.text = "Duration: ${quiz.duration.toString()}"

                btnDelete.setOnClickListener {
                    listener?.onDelete(quiz)
                }
            }

        }
    }

    interface Listener {

        fun onDelete(quiz: Quiz)
    }


}