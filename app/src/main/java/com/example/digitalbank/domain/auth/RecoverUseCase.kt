package com.example.digitalbank.domain.auth

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSourceImp
import javax.inject.Inject

class RecoverUseCase @Inject constructor(
    private val authFirebaseDataSourceImp: AuthFirebaseDataSourceImp
) {
    suspend operator fun invoke(email: String){
        return authFirebaseDataSourceImp.recover(email)
    }
}