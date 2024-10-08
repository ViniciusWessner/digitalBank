package com.example.digitalbank.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.domain.transaction.GetTransactionsUseCase
import com.example.digitalbank.domain.wallet.GetWalletUseCase
import com.example.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWalletUseCase: GetWalletUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
): ViewModel() {

    fun getWallet() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val wallet = getWalletUseCase.invoke()

            emit(StateView.Sucess(wallet))

        }catch (ex: Exception){
            emit(StateView.Error(ex.message))
        }
    }

    fun getTransactions() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val transactions = getTransactionsUseCase.invoke()

            emit(StateView.Sucess(transactions))

        }catch (ex: Exception){
            emit(StateView.Error(ex.message))
        }
    }
}