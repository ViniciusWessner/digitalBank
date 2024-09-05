package com.example.digitalbank.data.model

import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.data.enum.TransactionType
import com.google.firebase.database.FirebaseDatabase

data class Transaction(
     var id: String = "",
     val operation : TransactionOperation? = null,
     val date: Long = 0,
     val amount: Float = 0f,
     var type: TransactionType? = null,
)
