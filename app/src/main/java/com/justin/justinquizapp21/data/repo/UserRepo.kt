package com.justin.justinquizapp21.data.repo

import com.justin.justinquizapp21.data.model.User

interface UserRepo {
    suspend fun addUser(user: User)
    suspend fun getUser(uid: String): User?
    suspend fun removeUser()
    suspend fun updateUser(user: User)
}