package com.map.ip.data.address.repository

import com.map.ip.common.data.Result
import com.map.ip.data.address.model.IpAddress
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
interface AddressIpRepository {

    suspend fun getAddressByIp(ip: String): Flow<Result<IpAddress>>

    suspend fun <T> Flow<Result<T>>.handleSuccessResult(
        resultHandler: (T) -> Unit
    ) = onEach { result ->
        if (result is Result.Success) {
            result.data?.let {
                resultHandler(it)
            }
        }
    }
}