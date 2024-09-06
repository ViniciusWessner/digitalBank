package com.example.digitalbank.data.repository.transaction

import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class TransactionDataSourceImp @Inject constructor(
    database: FirebaseDatabase
) : TransactionDataSource {


    private val transactionReference = database.reference //Criando um NÓ profile no banco
        .child("transaction")
        .child(FirebaseHelper.getUserId())


    override suspend fun saveTransaction(transaction: Transaction) {
        return suspendCoroutine { continuation ->
            transactionReference
                .child(transaction.id)
                .setValue(transaction).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val dateReference = transactionReference
                            .child(transaction.id)
                            .child("date")
                        dateReference.setValue(ServerValue.TIMESTAMP)

                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

    override suspend fun getTransactions(): List<Transaction> {
        return suspendCoroutine { continuation ->
            transactionReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //validar lista
                    val transactions = mutableListOf<Transaction>()

                    //ppercorrer todas as transacoes do ID do usuario e converter elas em um objeto de transacao
                    for(ds in snapshot.children){
                        val transaction = ds.getValue(Transaction::class.java)
                        transaction?.let { transactions.add(it) }

                    }
                    continuation.resumeWith(Result.success(transactions)) //se a lista estiver vazia

                }

                override fun onCancelled(error: DatabaseError) {
                    error.toException().let {
                        continuation.resumeWith(Result.failure(it))
                    }
                }

            })
        }
    }

}