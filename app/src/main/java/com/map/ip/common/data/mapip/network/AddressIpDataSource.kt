package com.map.ip.common.data.mapip.network


import com.map.ip.common.data.base.BaseNetworkDataSource
import com.map.ip.common.data.base.ConnectivityState
import com.map.ip.common.data.base.NetworkErrorMapper
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