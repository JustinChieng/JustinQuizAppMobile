package com.justin.justinquizapp21.data.model

data class User(
    val id: String? = null,
    val name: String,
    val email: String,
    val role: String,
    val results: List<Score> = emptyList() // Include the Results subcollection
) {
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "name" to name,
            "email" to email,
            "role" to role,
            "results" to results.map { it.toHashMap() } // Convert each Score to HashMap
        )
    }

    companion object {
        fun fromHashMap(hash: Map<String, Any>): User {
            val resultsList = (hash["results"] as List<Map<String, Any>>?)
                ?.map { Score.fromHashMap(it) } ?: emptyList()

            return User(
                id = hash["id"].toString(),
                name = hash["name"].toString(),
                email = hash["email"].toString(),
                role = hash["role"].toString(),
                results = resultsList
            )
        }
    }
}
