package com.map.ip.main.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.map.ip.common.usecase.AddressIpUseCase
import com.map.ip.common.viewmodel.BaseStateViewModel
import com.map.ip.main.IpAddressNavigation
import com.map.ip.main.IpAddressViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@HiltViewModel
class IpAddressViewModel @Inject constructor(
    application: Application,
    private val addressUseCase: AddressIpUseCase
) : BaseStateViewModel<IpAddressViewState, IpAddressNavigation>(application) {

    fun fetchAddressByIp(ip: String) {
        if (validateIpAddress(ip)) {
            viewModelScope.launch(IO) {
                postProgressState()
                addressUseCase.fetchAddressByIp(ip).collect {
                    handleResult(it,
                        onSuccess = { result ->
                            postViewState(IpAddressViewState.Address(result!!))
                        })
                }
            }
        } else {
            postViewState(IpAddressViewState.IpValidationError)
        }
    }

    private fun validateIpAddress(ip: String) = Patterns.IP_ADDRESS.matcher(ip).matches()
}