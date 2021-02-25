package com.map.ip.main

import com.map.ip.common.viewmodel.ViewState
import com.map.ip.main.models.IpAddressUi

sealed class IpAddressViewState : ViewState {
    data class Address(val address: IpAddressUi) : IpAddressViewState()
    data class IpAddress(val ip: String) : IpAddressViewState()
    object IpValidationError : IpAddressViewState()
}