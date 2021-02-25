package com.map.ip.di.address

import android.content.Context
import android.net.ConnectivityManager
import com.map.ip.common.data.ConnectivityState
import com.map.ip.data.address.network.AddressIpRetrofitBuilder
import com.map.ip.data.address.network.AddressIpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideApiService(): AddressIpService {
        return AddressIpRetrofitBuilder.createApiService()
    }

    @Provides
    fun provideConnectivityState(@ApplicationContext context: Context): ConnectivityState {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return ConnectivityState(connectivityManager)
    }
}