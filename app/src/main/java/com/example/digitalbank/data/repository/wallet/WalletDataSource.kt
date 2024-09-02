package com.example.digitalbank.data.repository.wallet

import com.example.digitalbank.data.model.Wallet

interface WalletDataSource {

    suspend fun initWallet(wallet: Wallet)

    suspend fun getWallet(): Wallet
}