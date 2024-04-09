package com.justin.justinquizapp21.data.model

data class Question(
    var id: String? =null,
    val title: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: String
) {
    fun toHashMap(): Map<String,String> {
        return mapOf(
            "title" to title,
            "optionA" to optionA,
            "optionB" to optionB,
            "optionC" to optionC,
            "optionD" to optionD,
            "correctAnswer" to correctAnswer
        )
    }
    companion object {
        fun fromHashMap(hash: Map<String, Any>) : Question {
            return Question(
                id = hash["id"].toString(),
                title = hash["title"].toString(),
                optionA = hash["optionA"].toString(),
                optionB = hash["optionB"].toString(),
                optionC = hash["optionC"].toString(),
                optionD = hash["optionD"].toString(),
                correctAnswer = hash["correctAnswer"].toString()
            )
        }
    }
}
