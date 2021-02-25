package com.map.ip.common.data

const val ERROR_UNAUTHORIZED = "Unauthorized"
private const val ERROR_BAD_REQUEST = "BadRequest"

sealed class Result<out R> {

    data class Success<out R>(val data: R?) : Result<R>()
    data class Error(val error: BaseError) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
        }
    }
}

abstract class BaseError(
    message: String = "",
    @Suppress("UNUSED_PARAMETER") cause: Throwable? = null
) : Throwable(message)

sealed class NetworkError(errorMessage: String = "", cause: Throwable? = null) :
    BaseError(errorMessage, cause) {
    object NoConnection : NetworkError()
    data class Unauthorized(val errorMessage: String = ERROR_UNAUTHORIZED) :
        NetworkError(errorMessage = errorMessage)

    data class BadRequest(val responseCode: Int, override val message: String) :
        NetworkError(ERROR_BAD_REQUEST)

    data class Unknown(val errorMessage: String = "", override val cause: Throwable? = null) :
        NetworkError(errorMessage, cause)
}