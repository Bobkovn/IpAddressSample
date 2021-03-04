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

    override suspend fun getAddressByIp(ip: String) =
        addressIpDataSource.getAddressByIp(ip)
}