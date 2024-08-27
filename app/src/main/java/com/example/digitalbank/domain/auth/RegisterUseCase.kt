package com.example.digitalbank.domain.auth

import com.example.digitalbank.data.model.User
import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSourceImp
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authFirebaseDataSourceImp: AuthFirebaseDataSourceImp
) {
    suspend operator fun invoke(user: User): User{
        return authFirebaseDataSourceImp.register(user)
    }
}