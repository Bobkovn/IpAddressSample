package com.map.ip.data.address.network


import com.map.ip.common.data.BaseNetworkDataSource
import com.map.ip.common.data.ConnectivityState
import com.map.ip.common.data.NetworkErrorMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
class AddressIpDataSource @Inject constructor(
    private val addressIpService: AddressIpService,
    connectivityState: ConnectivityState,
    networkErrorMapper: NetworkErrorMapper
) : BaseNetworkDataSource(connectivityState, networkErrorMapper) {

    fun getAddressByIp(ip: String) =
        addressIpService.getAddressByIp(ip).asFlow()
}