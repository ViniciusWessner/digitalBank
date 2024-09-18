package com.example.digitalbank.presenter.features.extract

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.domain.transaction.GetTransactionsUseCase
import com.example.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ExtractViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase
): ViewModel() {


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