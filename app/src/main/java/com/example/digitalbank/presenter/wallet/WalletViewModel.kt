package com.example.digitalbank.presenter.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.data.model.User
import com.example.digitalbank.data.model.Wallet
import com.example.digitalbank.domain.wallet.InitWalletUseCase
import com.example.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val initWalletUseCase: InitWalletUseCase
): ViewModel() {

    fun initWallet(wallet: Wallet) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            initWalletUseCase.invoke(wallet)

            emit(StateView.Sucess(null))

        }catch (ex: Exception){
            emit(StateView.Error(ex.message))
        }
    }
}