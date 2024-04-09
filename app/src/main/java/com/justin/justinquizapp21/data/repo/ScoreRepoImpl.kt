package com.justin.justinquizapp21.data.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.data.model.Score
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ScoreRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ScoreRepo {

    private fun getDbRef(): CollectionReference {
        val firebaseUser = authService.getCurrentUser()
        return firebaseUser?.uid?.let {
            db.collection("Results")
        } ?: throw Exception("No authentic user found")
    }

    override suspend fun addResult(score: Score) {
        getDbRef().add(score.toHashMap()).await()
    }

    override suspend fun getResult() = callbackFlow<List<Score>> {
        val listener = getDbRef().addSnapshotListener { value, error ->
            if (error != null) {
                throw error
            }
            val scores = mutableListOf<Score>()
            value?.documents?.let { docs ->
                for (doc in docs) {
                    doc.data?.let {
                        it["id"] = doc.id
                        scores.add(Score.fromHashMap(it))
                    }
                }
                trySend(scores)
            }
        }
        awaitClose {
            listener.remove()
        }
    }
}
