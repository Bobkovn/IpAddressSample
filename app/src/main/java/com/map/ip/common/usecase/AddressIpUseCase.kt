package com.map.ip.common.usecase

import com.map.ip.common.data.mapip.model.IpAddress
import com.map.ip.common.data.mapip.repository.AddressIpRepository
import com.map.ip.main.models.IpAddressUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AddressIpUseCase @Inject constructor(private val repository: AddressIpRepository) : BaseUseCase() {

    suspend fun fetchAddressByIp(ip: String) =
        repository.getAddressByIp(ip).mapResult { result ->
            mapAddressUi(result)
        }

    private fun mapAddressUi(address: IpAddress) = address.let {
        IpAddressUi(
            it.country,
            it.regionName,
            it.city,
            it.zip,
            it.latitude,
            it.longitude
        )
    }
}