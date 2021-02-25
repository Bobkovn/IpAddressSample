package com.map.ip.main.ui

import com.map.ip.R
import com.map.ip.common.ui.BaseViewModelFragment
import com.map.ip.common.utils.clearErrorAfterTextChanged
import com.map.ip.main.IpAddressNavigation
import com.map.ip.main.IpAddressViewState
import com.map.ip.main.models.IpAddressUi
import com.map.ip.main.viewmodel.IpAddressViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class IpAddressFragment :
    BaseViewModelFragment<IpAddressViewModel, IpAddressViewState, IpAddressNavigation>() {

    override val viewModelClass: Class<IpAddressViewModel>
        get() = IpAddressViewModel::class.java

    override val layout: Int
        get() = R.layout.fragment_main

    override fun setupViews() {
        addressDataBtn.setOnClickListener {
            viewModel.fetchAddressByIp(ipEditText.text.toString())
        }
        ipInputLayout.clearErrorAfterTextChanged()
    }

    override fun setViewState(viewState: IpAddressViewState) {
        when (viewState) {
            is IpAddressViewState.Address -> setupAddressViewState(viewState.address)
            is IpAddressViewState.IpAddress -> setupIpViewState(viewState.ip)
            is IpAddressViewState.IpValidationError -> setupValidationError()
        }
    }

    private fun setupValidationError() {
        ipInputLayout.error = getString(R.string.error_ip)
    }

    private fun setupAddressViewState(address: IpAddressUi) {
        addressDataTV.text = address.toString()
    }

    private fun setupIpViewState(ip: String) {
        ipEditText.setText(ip)
    }

    override fun navigateTo(navigation: IpAddressNavigation) {
    }
}