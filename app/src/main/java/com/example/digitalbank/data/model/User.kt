package com.example.digitalbank.data.model

import com.google.firebase.database.Exclude

data class User(
    val id: String? = "",
    val name: String = "",
    val email: String = "",
    val celular: String = "",
    @get:Exclude
    val senha: String = "" //nao salvar no firebase usando o Exclude
)
