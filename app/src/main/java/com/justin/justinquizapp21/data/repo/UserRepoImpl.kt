package com.justin.justinquizapp21.data.repo

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.justinquizapp21.core.service.AuthService
import com.justin.justinquizapp21.core.service.AuthServiceImpl
import com.justin.justinquizapp21.data.model.User
import kotlinx.coroutines.tasks.await

class UserRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserRepo{

    private fun getDbRef(): CollectionReference {
        return db.collection("users")
    }

    private fun getUid(): String {
        val firebaseUser = authService.getCurrentUser()
        return firebaseUser?.uid ?: throw Exception("No Authentic user found")
    }

    override suspend fun addUser(user: User) {
        getDbRef().document(getUid()).set(user.toHashMap()).await()
    }

    override suspend fun getUser(uid: String): User? {
        val doc = getDbRef().document(getUid()).get().await()
        return doc.data?. let{
            it["id"] = getUid()
            User.fromHashMap(it)
        }
    }

    override suspend fun removeUser() {
        getDbRef().document(getUid()).delete().await()
    }

    override suspend fun updateUser(user: User) {
        getDbRef().document(getUid()).set(user.toHashMap()).await()
    }


}