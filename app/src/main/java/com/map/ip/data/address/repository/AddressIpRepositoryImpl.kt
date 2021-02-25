package com.map.ip.data.address.repository

import com.map.ip.data.address.network.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class AddressIpRepositoryImpl @Inject constructor(
    private val addressIpDataSource: AddressIpDataSource
) : AddressIpRepository {

    companion object {

        @JvmStatic
        @Volatile
        private var INSTANCE: AddressIpRepository? = null

        @JvmStatic
        fun init(
            addressIpDataSource: AddressIpDataSource,
        ) {
            if (INSTANCE == null) {
                INSTANCE = AddressIpRepositoryImpl(addressIpDataSource)
            }
        }

        @JvmStatic
        fun getInstance(): AddressIpRepository = synchronized(this) {
            INSTANCE?.let {
                return INSTANCE as AddressIpRepository
            } ?: throw RuntimeException("need to init Repository")
        }
    }

    override suspend fun getAddressByIp(ip: String) =
        addressIpDataSource.getAddressByIp(ip)
}