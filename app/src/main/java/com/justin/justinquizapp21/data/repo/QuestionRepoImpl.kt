package com.justin.justinquizapp21.data.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.data.model.Question
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuestionRepoImpl (
    private val authService: AuthService,
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

): QuestionRepo {

    private fun getDbRef(): CollectionReference {
        val firebaseUser = authService.getCurrentUser()
        return firebaseUser?.uid?.let {
            db.collection("questions")
        } ?: throw Exception("No authentic user found")
    }

    override suspend fun getQuestions() = callbackFlow {
        val listener = getDbRef().addSnapshotListener { value, error ->
            if (error != null) {
                throw error
            }
            val questions = mutableListOf<Question>()
            value?.documents?.let{ docs ->
                for (doc in docs) {
                    doc.data?.let{
                        it["id"] = doc.id
                        questions.add(Question.fromHashMap(it))
                    }
                }
                trySend(questions)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun addQuestion(question: Question) {
        if (question.id != null) {
            throw IllegalArgumentException("Question should not have an ID when adding to Firestore")
        }
        getDbRef().add(question.toHashMap()).await()
    }

    override suspend fun getQuestion(id: String): Question? {
        val doc = getDbRef().document(id).get().await()
        return doc.data?.let {
            it["id"] = doc.id
            Question.fromHashMap(it)
        }
    }

    override suspend fun deleteQuestion(id: String) {
        getDbRef().document(id).delete().await()
    }
}