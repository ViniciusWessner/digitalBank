package com.example.digitalbank.domain.recharge

import com.example.digitalbank.data.model.Recharge
import com.example.digitalbank.data.repository.recharge.RechargeDataSourceImp
import javax.inject.Inject

class SaveRechargeUseCase @Inject constructor(
    private val rechargeDataSourceImp: RechargeDataSourceImp
) {

    suspend operator fun invoke(recharge: Recharge): Recharge {
        return rechargeDataSourceImp.saveRecharge(recharge)
    }

}
