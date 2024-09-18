package com.example.digitalbank.data.enum

enum class TransactionOperation {
    DEPOSIT,
    RECHARGE;

    companion object{
        fun getOperation(operation: TransactionOperation) : String{
            return when(operation) {
                DEPOSIT -> {
                    "Depósito"
                }
                RECHARGE -> {
                    "Recarga"
                }
                else -> ""
            }
        }
    }
}