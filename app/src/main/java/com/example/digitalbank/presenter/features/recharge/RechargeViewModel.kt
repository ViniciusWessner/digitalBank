package com.example.digitalbank.presenter.features.recharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.domain.recharge.GetRechargeUseCase
import com.example.digitalbank.domain.recharge.SaveRechargeUseCase
import com.example.digitalbank.domain.transaction.SaveTransactionUseCase
import com.example.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RechargeViewModel @Inject constructor(
    private val saveRechargeUseCase: SaveRechargeUseCase,
    private val saveTransactionUseCase: SaveTransactionUseCase
): ViewModel(){

    fun saveRecharge(recharge: Recharge) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val result = saveRechargeUseCase.invoke(recharge)

            emit(StateView.Sucess(result))

        }catch (ex: Exception){
            emit(StateView.Error(ex.message))
        }
    }

    fun saveTransaction(transaction: Transaction) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            saveTransactionUseCase.invoke(transaction)

            emit(StateView.Sucess(Unit))

        }catch (ex: Exception){
            emit(StateView.Error(ex.message))
        }
    }
}