package com.example.digitalbank.data.repository.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlin.coroutines.suspendCoroutine

class AuthFirebaseDataSourceImp(
   private val firebaseDatabase: FirebaseDatabase,
   private val firebaseAuth: FirebaseAuth
): AuthFirebaseDataSource {

    override suspend fun login(email: String, senha: String) {
        TODO("Not yet implemented")
    }

    override suspend fun register(nome: String, email: String, celular: String, senha: String): FirebaseUser {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = task.result.user
                        user?.let {
                            continuation.resumeWith(Result.success(it))
                        }
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

    override suspend fun recover(email: String) {
        TODO("Not yet implemented")
    }

}