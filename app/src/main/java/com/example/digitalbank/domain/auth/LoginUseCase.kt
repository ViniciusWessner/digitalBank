package com.example.digitalbank.domain.auth

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSourceImp

class LoginUseCase(
    private val authFirebaseDataSourceImp: AuthFirebaseDataSourceImp
) {
    suspend operator fun invoke(email: String, senha: String){
        return authFirebaseDataSourceImp.login(email, senha)
    }
}