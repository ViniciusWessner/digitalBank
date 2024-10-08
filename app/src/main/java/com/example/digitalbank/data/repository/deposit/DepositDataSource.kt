package com.example.digitalbank.data.repository.deposit

import com.example.digitalbank.data.model.Deposit

interface DepositDataSource {

    suspend fun saveDeposit(deposit: Deposit): Deposit

    suspend fun GetDeposit(id: String): Deposit
}