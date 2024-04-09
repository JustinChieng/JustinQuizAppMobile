package com.justin.justinquizapp21.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.justin.justinquizapp21.data.model.Score
import com.justin.justinquizapp21.databinding.ItemLayoutScoreBinding

class ScoresAdapter (
    private var scores: List<Score>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemLayoutScoreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScoreItemViewHolder(binding)
    }

    override fun getItemCount() = scores.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val score = scores[position]
        if (holder is ScoreItemViewHolder) {
            holder.bind(score)
        }
    }

    fun setScores(scores: List<Score>) {
        this.scores = scores
        notifyDataSetChanged()
    }

    inner class ScoreItemViewHolder(
        private val binding: ItemLayoutScoreBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(score: Score) {
            binding.run{
                tvStudentName.text = "Name: ${score.name}"
                tvScore.text = "Score: ${score.result}"
                tvId.text = "QuizId: ${score.quizId}"
            }
        }
    }


}