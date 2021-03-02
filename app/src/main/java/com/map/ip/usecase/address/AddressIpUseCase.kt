package com.map.ip.usecase.address

import com.map.ip.common.usecase.BaseUseCase
import com.map.ip.data.address.model.IpAddress
import com.map.ip.data.address.repository.AddressIpRepository
import com.map.ip.ui.address.models.AddressUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AddressIpUseCase @Inject constructor(private val repository: AddressIpRepository) :
    BaseUseCase<String, AddressUi>() {

    override suspend fun execute(data: String) =
        repository.getAddressByIp(data).mapResult { result ->
            mapAddressUi(result)
        }

    private fun mapAddressUi(address: IpAddress) = address.let {
        AddressUi(
            it.country,
            it.regionName,
            it.city,
            it.zip,
            it.latitude,
            it.longitude
        )
    }
}