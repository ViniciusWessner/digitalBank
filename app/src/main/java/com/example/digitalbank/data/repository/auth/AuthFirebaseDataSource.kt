package com.example.digitalbank.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

interface AuthFirebaseDataSource {

    //login
    suspend fun login(
        email: String,
        senha: String,
    )
    
    //registro
    suspend fun register(
        nome: String,
        email: String,
        celular: String,
        senha: String
    ): FirebaseUser

    //recuperar senha
    suspend fun recover (
        email: String
    )
}