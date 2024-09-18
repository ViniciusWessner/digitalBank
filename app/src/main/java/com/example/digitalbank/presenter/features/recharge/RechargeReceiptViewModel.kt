package com.example.digitalbank.presenter.features.recharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.domain.deposit.GetDepositUseCase
import com.example.digitalbank.domain.recharge.GetRechargeUseCase
import com.example.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RechargeReceiptViewModel @Inject constructor(
    private val getRechargeUseCase: GetRechargeUseCase
): ViewModel() {


    fun getRecharge(id: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val deposit = getRechargeUseCase.invoke(id)

            emit(StateView.Sucess(deposit))

        }catch (ex: Exception){
            emit(StateView.Error(ex.message))
        }
    }

}