package com.example.digitalbank.di

import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSource
import com.example.digitalbank.data.repository.auth.AuthFirebaseDataSourceImp
import com.example.digitalbank.data.repository.deposit.DepositDataSource
import com.example.digitalbank.data.repository.deposit.DepositDataSourceImp
import com.example.digitalbank.data.repository.recharge.RechargeDataSource
import com.example.digitalbank.data.repository.recharge.RechargeDataSourceImp
import com.example.digitalbank.data.repository.transaction.TransactionDataSource
import com.example.digitalbank.data.repository.transaction.TransactionDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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

    @Binds
    abstract fun bindsTransactionDataSource(
        transactionDataSourceImp: TransactionDataSourceImp
    ): TransactionDataSource

    @Binds
    abstract fun bindsRechargeDataSource(
        rechargeDataSourceImp: RechargeDataSourceImp
    ): RechargeDataSource
}