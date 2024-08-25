package com.example.digitalbank.di

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource
import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsAuthRepository(
        authFirebaseDataSourceImp: AuthFirebaseDataSourceImp
    ): AuthFirebaseDataSource
}