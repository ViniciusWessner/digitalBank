package com.example.digitalbank.data.model

import android.os.Parcelable
import com.example.digitalbank.util.FirebaseHelper
import com.google.firebase.database.FirebaseDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deposit(
    var id: String = "",
    var date: Long = 0,
    var amount: Float = 0f
): Parcelable{
    init {
        this.id = FirebaseHelper.generateId()
    }
}
