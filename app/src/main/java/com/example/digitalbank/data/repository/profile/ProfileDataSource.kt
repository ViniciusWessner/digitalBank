package com.example.digitalbank.data.repository.profile

import com.example.digitalbank.data.model.User

interface ProfileDataSource {

    suspend fun saveProfile(user: User)

    suspend fun getProfile(): User
}