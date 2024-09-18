package com.example.digitalbank.data.enum

import com.example.digitalbank.data.enum.TransactionOperation.DEPOSIT
import com.example.digitalbank.data.enum.TransactionOperation.RECHARGE

enum class TransactionType {
    CASH_IN,
    CASH_OUT ;

    companion object{
        fun getType(operation: TransactionOperation) : Char{
            return when(operation) {
                DEPOSIT -> {
                    'D'
                }
                RECHARGE -> {
                    'R'
                }
            }
        }
    }
}