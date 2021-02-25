package com.map.ip.ui.address.ui

import com.map.ip.R
import com.map.ip.common.ui.BaseViewModelFragment
import com.map.ip.common.utils.clearErrorAfterTextChanged
import com.map.ip.ui.address.AddressNavigation
import com.map.ip.ui.address.AddressViewState
import com.map.ip.ui.address.models.AddressUi
import com.map.ip.ui.address.viewmodel.AddressViewModel
import kotlinx.android.synthetic.main.fragment_address.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class AddressFragment :
    BaseViewModelFragment<AddressViewModel, AddressViewState, AddressNavigation>() {

    override val viewModelClass: Class<AddressViewModel>
        get() = AddressViewModel::class.java

    override val layout: Int
        get() = R.layout.fragment_address

    override fun setupViews() {
        addressDataBtn.setOnClickListener {
            viewModel.fetchAddressByIp(ipEditText.text.toString())
        }
        ipInputLayout.clearErrorAfterTextChanged()
    }

    override fun setViewState(viewState: AddressViewState) {
        when (viewState) {
            is AddressViewState.Address -> setupAddressViewState(viewState.address)
            is AddressViewState.IpAddress -> setupIpViewState(viewState.ip)
            is AddressViewState.IpValidationError -> setupValidationError()
        }
    }

    private fun setupValidationError() {
        ipInputLayout.error = getString(R.string.error_ip)
    }

    private fun setupAddressViewState(address: AddressUi) {
        addressDataTV.text = address.toString()
    }

    private fun setupIpViewState(ip: String) {
        ipEditText.setText(ip)
    }

    override fun navigateTo(navigation: AddressNavigation) {
    }
}