package com.example.digitalbank.data.model

import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase

data class Deposit(
    var id: String = "",
    var date: Long = 0,
    var amount: Float = 0f
){
    init {
        this.id = FirebaseHelper.generateId()
    }
}
