package com.justin.justinquizapp21.core.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.coroutines.tasks.await

class AuthServiceImpl(
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
): AuthService {

    override suspend fun register(email: String, pass: String): FirebaseUser? {
        val task = auth.createUserWithEmailAndPassword(email, pass).await()
        return task.user
    }

    override suspend fun login(email: String, pass: String): FirebaseUser? {
        val task = auth.signInWithEmailAndPassword(email, pass).await()
        return task.user
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun logout() {
        auth.signOut()
    }
}