package com.example.digitalbank.data.repository.transaction

import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class TransactionDataSourceImp @Inject constructor(
    database: FirebaseDatabase
): TransactionDataSource{


    private val transactionReference = database.reference //Criando um NÓ profile no banco
        .child("transaction")
        .child(FirebaseHelper.getUserId())


    override suspend fun saveTransaction(transaction: Transaction) {
        return suspendCoroutine { continuation ->
            transactionReference
                .child(transaction.id)
                .setValue(transaction).addOnCompleteListener{ task ->
                    if (task.isSuccessful) {

                        val dateReference = transactionReference
                            .child(transaction.id)
                            .child("date") //possivelmente uma diferenca de tempo

                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

}