package com.map.ip.common.ui

import android.os.Bundle
import android.view.View
import com.map.ip.common.MainActivity
import com.map.ip.common.viewmodel.BaseStateViewModel
import com.map.ip.common.viewmodel.Navigation
import com.map.ip.common.viewmodel.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
abstract class BaseViewModelFragment<VIEW_MODEL : BaseStateViewModel<VIEW_STATE, NAVIGATION>, VIEW_STATE : ViewState, NAVIGATION : Navigation> :
    BaseFragment() {

    abstract val viewModelClass: Class<VIEW_MODEL>

    open val viewModel: VIEW_MODEL by obtainViewModel(viewModelClass)

    private val viewStateObserver by lazy {
        { state: VIEW_STATE ->
            setProgressState(false)
            setViewState(state)
        }
    }

    private val navigationObserver by lazy {
        { navigation: NAVIGATION ->
            setProgressState(false)
            navigateTo(navigation)
        }
    }

    private val progressStateObserver by lazy {
        { _: Unit ->
            setProgressState(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates()
    }

    private fun setProgressState(inProgress: Boolean) =
        (activity as MainActivity).changeProgressBarVisibility(inProgress)

    abstract fun setViewState(viewState: VIEW_STATE)

    abstract fun navigateTo(navigation: NAVIGATION)

    private fun observeStates() {
        viewModel.apply {
            observeViewState(viewLifecycleOwner, viewStateObserver)
            observeNavigation(viewLifecycleOwner, navigationObserver)
            observeProgressViewState(viewLifecycleOwner, progressStateObserver)
            observeCommonErrors(viewLifecycleOwner, commonNetworkErrorObserver)
        }
    }
}