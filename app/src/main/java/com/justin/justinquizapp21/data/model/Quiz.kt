package com.justin.justinquizapp21.data.model

data class Quiz(
    val id: String? = null,
    val title: String,
    val quizid: String,
    val duration: Int,
    var questionCount: Int,
    val questions: List<Question> = emptyList(),
    val createdBy: String = ""
) {
    fun toHashMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "quizid" to quizid,
            "duration" to duration
        )
    }

    companion object {
        fun fromHashMap(hash: Map<String, Any> ): Quiz {
            return Quiz(
                id = hash["id"].toString(),
                title = hash["title"].toString(),
                quizid = hash["quizid"].toString(),
                duration = (hash["duration"] as? Long)?.toInt() ?: 0,
                questionCount = (hash["questionCount"] as? Long)?.toInt() ?: 0,
            )
        }
    }
}

