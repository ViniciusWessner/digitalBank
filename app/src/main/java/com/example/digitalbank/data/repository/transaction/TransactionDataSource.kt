package com.example.digitalbank.data.repository.transaction

import com.example.digitalbank.data.model.Transaction

interface TransactionDataSource {

    suspend fun saveTransaction(transaction: Transaction)

}