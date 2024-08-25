package com.example.digitalbank.domain.auth

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSourceImp

class RegisterUseCase(
    private val authFirebaseDataSourceImp: AuthFirebaseDataSourceImp
) {
    suspend operator fun invoke(nome: String, email: String, celular: String, senha: String){
        return authFirebaseDataSourceImp.register(nome, email, celular, senha)
    }
}