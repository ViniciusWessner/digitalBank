package com.example.digitalbank.data.repository.deposit

import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class DepositDataSourceImp @Inject constructor(
    database: FirebaseDatabase
) : DepositDataSource {

    private val depositReference = database.reference //Criando um NÓ profile no banco
        .child("deposit")
        .child(FirebaseHelper.getUserId())

    override suspend fun saveDeposit(deposit: Deposit): Deposit {
        return suspendCoroutine { continuation ->
            depositReference
                .child(deposit.id)
                .setValue(deposit).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val dateReference = depositReference
                            .child(deposit.id)
                            .child("date")

                        dateReference.setValue(ServerValue.TIMESTAMP) //seta o valor do horariod e deposito
                            .addOnCompleteListener { taskSetDate ->
                                if (taskSetDate.isSuccessful) {
                                    continuation.resumeWith(Result.success(deposit))
                                } else {
                                    taskSetDate.exception?.let {
                                        continuation.resumeWith(Result.failure(it))
                                    }
                                }
                            }
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }

        }
    }

}