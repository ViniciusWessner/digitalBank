package com.example.digitalbank.domain.deposit

import com.example.digitalbank.data.model.Deposit
import com.example.digitalbank.data.repository.deposit.DepositDataSourceImp
import javax.inject.Inject

class GetDepositUseCase @Inject constructor(
    private val depositDataSourceImp: DepositDataSourceImp
) {

    //consultando um deposito com o ID
    suspend operator fun invoke(id: String): Deposit {
        return depositDataSourceImp.GetDeposit(id)
    }

}