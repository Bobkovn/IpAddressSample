package com.map.ip.ui.address.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.map.ip.usecase.address.AddressUseCase
import com.map.ip.common.viewmodel.BaseStateViewModel
import com.map.ip.ui.address.AddressNavigation
import com.map.ip.ui.address.AddressViewState
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
class AddressViewModel @Inject constructor(
    application: Application,
    private val addressUseCase: AddressUseCase
) : BaseStateViewModel<AddressViewState, AddressNavigation>(application) {

    fun fetchAddressByIp(ip: String) {
        if (validateIpAddress(ip)) {
            viewModelScope.launch(IO) {
                postProgressState()
                addressUseCase.fetchAddressByIp(ip).collect {
                    handleResult(it,
                        onSuccess = { result ->
                            postViewState(AddressViewState.Address(result!!))
                        })
                }
            }
        } else {
            postViewState(AddressViewState.IpValidationError)
        }
    }

    private fun validateIpAddress(ip: String) = Patterns.IP_ADDRESS.matcher(ip).matches()
}