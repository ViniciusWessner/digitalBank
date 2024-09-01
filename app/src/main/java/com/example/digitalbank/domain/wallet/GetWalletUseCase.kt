package com.example.digitalbank.domain.wallet

import com.example.digitalbank.data.model.User
import com.example.digitalbank.data.model.Wallet
import com.example.digitalbank.data.repository.wallet.WalletDataSource
import com.example.digitalbank.data.repository.wallet.WalletDataSourceImp
import javax.inject.Inject

class GetWalletUseCase @Inject constructor(
    private val walletDataSourceImp: WalletDataSourceImp
) {

    suspend operator fun invoke(): Wallet{
        return walletDataSourceImp.getWallet()
    }
}