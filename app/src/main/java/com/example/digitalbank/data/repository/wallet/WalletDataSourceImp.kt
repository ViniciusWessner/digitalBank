package com.example.digitalbank.data.repository.wallet

import com.example.digitalbank.data.model.Wallet
import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class WalletDataSourceImp @Inject constructor(
    database: FirebaseDatabase
) : WalletDataSource {

    private val walletReference = database.reference //Criando um NÓ wallet no banco
        .child("wallet")

    override suspend fun initWallet(wallet: Wallet) {
        return suspendCoroutine { continuation ->
            walletReference.child(wallet.id)
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
}