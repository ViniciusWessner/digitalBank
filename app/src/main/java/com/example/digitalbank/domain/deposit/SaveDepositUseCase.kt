package com.example.digitalbank.domain.deposit

import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.data.repository.deposit.DepositDataSourceImp
import javax.inject.Inject

class SaveDepositUseCase @Inject constructor(
    private val depositDataSourceImp: DepositDataSourceImp
) {

    //retorna o id do deposito
    suspend operator fun invoke(deposit: Deposit): Deposit {
        return depositDataSourceImp.saveDeposit(deposit)
    }

}