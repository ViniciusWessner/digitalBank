package com.example.digitalbank.data.repository.recharge

import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class RechargeDataSourceImp @Inject constructor(
    database: FirebaseDatabase
) : RechargeDataSource {

    private val rechargeReference = database.reference //Criando um NÓ profile no banco
        .child("recharge")
        .child(FirebaseHelper.getUserId())


    override suspend fun saveRecharge(recharge: Recharge): Recharge {
        return suspendCoroutine { continuation ->
            rechargeReference
                .child(recharge.id)
                .setValue(recharge).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val dateReference = rechargeReference
                            .child(recharge.id)
                            .child("date")

                        dateReference.setValue(ServerValue.TIMESTAMP) //seta o valor do horariod e deposito
                            .addOnCompleteListener { taskSetDate ->
                                if (taskSetDate.isSuccessful) {
                                    continuation.resumeWith(Result.success(recharge))
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

    override suspend fun getRecharge(id: String): Recharge { //recuperar o deposito no firebase usando o ID
        return suspendCoroutine { continuation ->
            rechargeReference
                .child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val recharge = snapshot.getValue(Recharge::class.java)
                        recharge?.let {
                            continuation.resumeWith(Result.success(it))
                        }

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