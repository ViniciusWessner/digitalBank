package com.example.digitalbank.data.repository.auth

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
    )

    //recuperar senha
    suspend fun recover (
        email: String
    )
}