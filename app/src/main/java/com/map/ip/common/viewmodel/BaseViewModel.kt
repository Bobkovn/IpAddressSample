package com.map.ip.common.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import com.map.ip.common.data.BaseError
import com.map.ip.common.data.NetworkError
import com.map.ip.common.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
abstract class BaseViewModel(application: Application) :
    AndroidViewModel(application), CoroutineScope {

    private val commonNetworkError = SingleLiveEvent<NetworkError>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    protected fun handleCommonErrors(
        error: BaseError,
        errorsHandler: ErrorHandler = ErrorHandler()
    ) {
        fun onCommonError(error: BaseError) = commonNetworkError.postValue(error as NetworkError)
        when {
            error is NetworkError.Unauthorized -> errorsHandler.onUnauthorizedError?.invoke(error)
                ?: onCommonError(error)
            error is NetworkError.NoConnection -> errorsHandler.onConnectionError?.invoke(error)
                ?: onCommonError(error)
            (error is NetworkError.BadRequest && errorsHandler.onBadRequestError != null) ->
                errorsHandler.onBadRequestError.invoke(
                    error
                )
            else -> errorsHandler.onError?.invoke(error) ?: onCommonError(error)
        }
    }

    fun observeCommonErrors(owner: LifecycleOwner, observer: (BaseError) -> Unit) {
        commonNetworkError.observe<NetworkError>(owner, observer)
    }

    protected inline fun <T> handleResult(
        result: Result<T?>,
        onSuccess: (T?) -> Unit = {},
        onError: ErrorHandler = ErrorHandler()
    ) {
        when (result) {
            is Result.Success -> onSuccess(result.data)
            is Result.Error -> handleCommonErrors(result.error, onError)
        }
    }
}

class ErrorHandler(
    val onUnauthorizedError: ((NetworkError.Unauthorized) -> Unit)? = null,
    val onConnectionError: ((NetworkError.NoConnection) -> Unit)? = null,
    val onBadRequestError: ((NetworkError.BadRequest) -> Unit)? = null,
    val onError: ((BaseError) -> Unit)? = null
) : (BaseError) -> Unit {
    override fun invoke(error: BaseError) = onError?.invoke(error) ?: Unit
}