package com.map.ip.common.viewmodel

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
abstract class BaseStateViewModel<VIEW_STATE : ViewState, NAVIGATION : Navigation>(
    application: Application
) :
    BaseViewModel(application) {

    private val viewState = SingleLiveEvent<VIEW_STATE>()
    private val navigationState = SingleLiveEvent<NAVIGATION>()
    private val progressViewState = SingleLiveEvent<Unit>()

    fun observeViewState(owner: LifecycleOwner, observer: (VIEW_STATE) -> Unit) {
        viewState.observe<VIEW_STATE>(owner, observer)
        if (viewState.value != null) {
            viewState.postValue(viewState.value)
        }
    }

    fun observeNavigation(owner: LifecycleOwner, observer: (NAVIGATION) -> Unit) =
        navigationState.observe<NAVIGATION>(owner, observer)

    fun observeProgressViewState(owner: LifecycleOwner, observer: (Unit) -> Unit) =
        progressViewState.observe<Unit>(owner, observer)

    protected fun postViewState(newViewState: VIEW_STATE, delayMillis: Long = 0L): Job {
        return viewModelScope.launch(Dispatchers.Main) {
            if (delayMillis > 0) delay(delayMillis)
            viewState.value = newViewState
        }
    }

    protected fun postProgressState(): Job {
        return viewModelScope.launch(Dispatchers.Main) {
            progressViewState.value = Unit
        }
    }

    protected fun navigateTo(navigation: NAVIGATION) {
        navigationState.postValue(navigation)
    }
}