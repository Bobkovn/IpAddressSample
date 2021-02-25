package com.map.ip.di.address

import com.map.ip.data.address.repository.AddressIpRepository
import com.map.ip.data.address.repository.AddressIpRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideRepository(repository: AddressIpRepositoryImpl): AddressIpRepository {
        return repository
    }
}