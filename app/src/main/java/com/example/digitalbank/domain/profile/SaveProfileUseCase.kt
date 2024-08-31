package com.example.digitalbank.domain.profile

import com.example.digitalbank.data.model.User
import com.example.digitalbank.data.repository.profile.ProfileRepositoryImp
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val profileRepositoryImp: ProfileRepositoryImp
) {

    suspend operator fun invoke(user: User){
        return profileRepositoryImp.saveProfile(user)
    }
}