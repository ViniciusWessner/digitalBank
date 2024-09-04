package com.example.digitalbank.data.repository.wallet

import com.example.digitalbank.data.model.Wallet
import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class WalletDataSourceImp @Inject constructor(
    database: FirebaseDatabase
) : WalletDataSource {

    private val walletReference = database.reference //Criando um NÓ wallet no banco
        .child("wallet")
        .child(FirebaseHelper.getUserId())
    override suspend fun initWallet(wallet: Wallet) {
        return suspendCoroutine { continuation ->
            walletReference.child(FirebaseHelper.getUserId())
            walletReference.setValue(wallet).addOnCompleteListener { task ->
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

    override suspend fun getWallet(): Wallet {
        return suspendCoroutine { continuation ->
            walletReference.child(FirebaseHelper.getUserId())
            walletReference.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val wallet = snapshot.getValue(Wallet::class.java)

                    wallet?.let { continuation.resumeWith(Result.success(wallet)) }
                }

                override fun onCancelled(error: DatabaseError) { //se tiver executando e cancela
                    continuation.resumeWith(Result.failure(error.toException()))
                }

            })
        }
    }
}