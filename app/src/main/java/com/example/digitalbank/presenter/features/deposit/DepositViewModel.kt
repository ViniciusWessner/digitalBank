package com.example.digitalbank.presenter.features.deposit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.domain.deposit.SaveDepositUseCase
import com.example.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DepositViewModel @Inject constructor(
    private val saveDepositUseCase: SaveDepositUseCase
): ViewModel() {

    fun saveDeposit(deposit: Deposit) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val idDeposit = saveDepositUseCase.invoke(deposit)

            emit(StateView.Sucess(idDeposit))

        }catch (ex: Exception){
            emit(StateView.Error(ex.message))
        }
    }
}