package com.map.ip.common.usecase

import com.map.ip.common.data.NetworkError
import com.map.ip.common.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class BaseUseCase {

    fun <T, R> Flow<Result<T>>.mapResult(
        resultMapper: (T) -> R
    ) = map { result ->
        if (result is Result.Success) {
            result.data?.let {
                Result.Success(resultMapper(it))
            } ?: Result.Error(
                NetworkError.Unknown("Unknown")
            )
        } else {
            result as Result.Error
        }
    }
}