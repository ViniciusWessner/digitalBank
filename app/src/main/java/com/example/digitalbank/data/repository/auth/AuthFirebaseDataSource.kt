package com.example.digitalbank.data.repository.auth

import com.example.digitalbank.data.model.User

interface AuthFirebaseDataSource {

    //login
    suspend fun login(
        email: String,
        senha: String,
    )
    
    //registro
    suspend fun register(
        user: User
    ): User

    //recuperar senha
    suspend fun recover (
        email: String
    )
}