package com.example.digitalbank.data.repository.recharge

import com.example.digitalbank.data.model.Recharge

interface RechargeDataSource {

    suspend fun saveRecharge(recharge: Recharge): Recharge

    suspend fun getRecharge(id: String): Recharge

}