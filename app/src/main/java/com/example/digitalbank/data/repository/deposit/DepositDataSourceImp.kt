package com.example.digitalbank.data.repository.deposit

import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class DepositDataSourceImp @Inject constructor(
    database: FirebaseDatabase
) : DepositDataSource {

    private val depositReference = database.reference //Criando um NÓ profile no banco
        .child("deposit")
        .child(FirebaseHelper.getUserId())

    override suspend fun saveDeposit(deposit: Deposit): String {
        return suspendCoroutine { continuation ->
            depositReference.child(deposit.id)
            depositReference.setValue(deposit).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resumeWith(Result.success(deposit.id))
                } else {
                    task.exception?.let {
                        continuation.resumeWith(Result.failure(it))
                    }
                }
            }

        }
    }

}