package com.example.digitalbank.presenter.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.digitalbank.data.model.User
import com.example.digitalbank.domain.profile.GetProfileUseCase
import com.example.digitalbank.domain.profile.SaveProfileUseCase
import com.example.digitalbank.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val  saveProfileUseCase: SaveProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase
): ViewModel(){

    fun saveProfile(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            saveProfileUseCase.invoke(user)

            emit(StateView.Sucess(null))

        }catch (ex: Exception){
            emit(StateView.Error(ex.message))
        }
    }

    fun getProfile() = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())

            val user = getProfileUseCase.invoke()

            emit(StateView.Sucess(user))

        }catch (ex: Exception){
            emit(StateView.Error(ex.message))
        }
    }



}