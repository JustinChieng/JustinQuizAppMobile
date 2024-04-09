package com.justin.justinquizapp21.data.repo

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.core.service.AuthServiceImpl
import com.justin.justinquizapp21.data.model.Question
import com.justin.justinquizapp21.data.model.Quiz
import com.justin.justinquizapp21.data.model.Score
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizsRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : QuizsRepo {

    private fun getDbRef(): CollectionReference {
        val firebaseUser = authService.getCurrentUser()
        return firebaseUser?.uid?.let {
            Log.d("another","quiz_collection")
            db.collection("quiz_collection")
        } ?: throw Exception("No authentic user found")
    }

    override suspend fun getQuizs() = callbackFlow {
        val listener = getDbRef().addSnapshotListener { value, error ->
            if (error != null) {
                throw error
            }
            Log.d("debugging", "testing")
            val quizs = mutableListOf<Quiz>()
            value?.documents?.let { docs ->
                for (doc in docs) {
                    doc.data?.let {
                        it["id"] = doc.id
                        quizs.add(Quiz.fromHashMap(it))
                    }
                }
                trySend(quizs)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    override suspend fun addQuiz(quiz: Quiz) {
        getDbRef().document(quiz.quizid)
            .set(quiz.toHashMap())
            .await()
    }

    override suspend fun getQuiz(id: String): Quiz? {
        Log.d("another", "questions_collection/$id/questions")
        val doc = getDbRef().document(id).get().await()
        val questionsDoc = db.collection("questions_collection/$id/questions")
            .get().await()
        val questions = mutableListOf<Question>()
        questionsDoc?.documents?.forEach { question ->
            question?.data?.let {
                questions.add(Question.fromHashMap(it))
            }
        }
        return doc.data?.let {
            it["id"] = doc.id
            Quiz.fromHashMap(it).copy(
                questions = questions
            )
        }
    }

    override suspend fun deleteQuiz(id: String) {
        getDbRef().document(id).delete().await()
    }




    override suspend fun getQuestionsForQuiz(quizId: String): List<Question> {
        val questionsDoc = db.collection("questions_collection/$quizId/questions")
            .get()
            .await()

        val questions = mutableListOf<Question>()

        questionsDoc?.documents?.forEach { question ->
            question?.data?.let {
                questions.add(Question.fromHashMap(it))
            }
        }

        return questions
    }


    override suspend fun getQuestionById(questionId: String): Question? {
        TODO("Not yet implemented")
    }

    override suspend fun addQuestionToQuiz(quizId: String, question: Question): String {
        // Add the question to the quiz's "questions" subcollection
        val addedQuestionRef = db.collection("questions_collection/$quizId/questions")
        addedQuestionRef.add(question)

        // Return the ID of the added question
        return addedQuestionRef.id
    }

    override suspend fun getQuestionCountForQuiz(quizId: String): Int {
        // Reference to the questions subcollection for the specified quiz
        val questionsRef = db.collection("questions_collection/$quizId/questions")

        return try {
            // Get the documents from the questions subcollection
            val questionsSnapshot = questionsRef.get().await()

            // Return the count of documents (questions) in the subcollection
            questionsSnapshot.size()
        } catch (e: Exception) {
            // Handle exceptions, e.g., if the quizId is invalid or there is a network error
            Log.e("QuizsRepoImpl", "Error getting question count for quiz $quizId", e)
            0
        }
    }


}
