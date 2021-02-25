package com.map.ip.ui.address

import com.map.ip.common.viewmodel.ViewState
import com.map.ip.ui.address.models.AddressUi

sealed class AddressViewState : ViewState {
    data class Address(val address: AddressUi) : AddressViewState()
    data class IpAddress(val ip: String) : AddressViewState()
    object IpValidationError : AddressViewState()
}