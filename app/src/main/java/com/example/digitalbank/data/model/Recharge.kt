package com.example.digitalbank.data.model

import android.os.Parcelable
import com.example.digitalbank.util.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recharge(
    var id: String = "",
    var date: Long = 0L,
    var amount: Float = 0F,
    var number: String = ""
) : Parcelable {
    init {
        this.id = FirebaseHelper.generateId()
    }
}