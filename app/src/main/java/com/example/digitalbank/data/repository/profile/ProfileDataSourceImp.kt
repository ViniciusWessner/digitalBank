package com.example.digitalbank.data.repository.profile

import com.example.digitalbank.data.model.User
import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class ProfileDataSourceImp @Inject constructor(
    private val database: FirebaseDatabase
) : ProfileDataSource {

    private val profileReference = database.reference //Criando um NÓ profile no banco
        .child("profile")
        .child(FirebaseHelper.getUserId())

    override suspend fun saveProfile(user: User) {
        return suspendCoroutine { continuation ->
            profileReference.setValue(user).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resumeWith(Result.success(Unit))
                } else {
                    task.exception?.let {
                        continuation.resumeWith(Result.failure(it))
                    }
                }
            }

        }
    }

    override suspend fun getProfile(): User { //somente vai ser chamada uma vez ao abrir a tela
        return suspendCoroutine { continuation ->
            profileReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //guarda resultado
                    val user = snapshot.getValue(User::class.java)
                    user?.let { continuation.resumeWith(Result.success(it)) }


                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resumeWith(Result.failure(error.toException()))

                }

            })

        }

    }
}