package com.map.ip.common.di

import com.map.ip.common.data.mapip.repository.AddressIpRepository
import com.map.ip.common.data.mapip.repository.AddressIpRepositoryImpl
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