package com.map.ip.common.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseNetworkDataSource(
    private val connectivityState: ConnectivityState,
    private val networkErrorMapper: NetworkErrorMapper
) {

    @ExperimentalCoroutinesApi
    protected fun <T> Call<T>.asFlow(): Flow<Result<T>> = callbackFlow {
        val scope: CoroutineScope = this
        var isFlowCancelled = false
        val callback = object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                if (isCanceled) return
                scope.launch {
                    val handledResponse = handleResponse(response)
                    offer(handledResponse)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if (isFlowCancelled) return
                offer(handleFailure(t))
            }
        }
        if (!connectivityState.isConnected()) {
            offer(Result.Error(NetworkError.NoConnection))
        } else if (!isExecuted && !isCanceled) {
            enqueue(callback)
        }
        awaitClose {
            isFlowCancelled = true
            cancel()
        }
    }

    private fun <T> handleResponse(
        response: Response<T>
    ) =
        when {
            response.isSuccessful -> response.body()?.let {
                Result.Success(it)
            } ?: Result.Success(null)
            else -> Result.Error(networkErrorMapper.toErrorCause(response = response))
        }

    private fun handleFailure(t: Throwable) =
        Result.Error(networkErrorMapper.toErrorCause<Any>(throwable = t))
}