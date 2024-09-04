package com.example.digitalbank.di

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource
import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSourceImp
import com.example.digitalbank.data.repository.deposit.DepositDataSource
import com.example.digitalbank.data.repository.deposit.DepositDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_components_ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsAuthRepositoryDataSource(
        authFirebaseDataSourceImp: AuthFirebaseDataSourceImp
    ): AuthFirebaseDataSource

    @Binds
    abstract fun bindsAuthDepositDataSource(
        depositDataSourceImp: DepositDataSourceImp
    ): DepositDataSource
}