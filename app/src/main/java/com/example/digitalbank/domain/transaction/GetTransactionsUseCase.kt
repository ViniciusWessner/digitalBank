package com.example.digitalbank.domain.transaction

import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.data.repository.transaction.TransactionDataSourceImp
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val transactionDataSourceImp: TransactionDataSourceImp
){

    suspend operator fun invoke() : List<Transaction>{
       return transactionDataSourceImp.getTransactions()
    }


}