package com.map.ip.common.data

import com.map.ip.common.data.HttpStatusCode.CODE_UNAUTHORIZED
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkErrorMapper @Inject constructor() {
    fun <T> toErrorCause(
        response: Response<T>? = null,
        throwable: Throwable? = null
    ): NetworkError = when {
        throwable?.isConnectionException == true -> NetworkError.NoConnection
        response?.isCodeUnauthorized == true -> NetworkError.Unauthorized(
            response.errorBody()?.message ?: ERROR_UNAUTHORIZED
        )
        response?.errorBody() != null -> NetworkError.BadRequest(
            response.code(), response.errorBody()!!.message
        )
        else -> NetworkError.Unknown(
            response?.toErrorString()
                ?: throwable?.toErrorString()
                ?: ""
        )
    }

    private fun Throwable.toErrorString() =
        when (this) {
            is HttpException -> "${code()}: $message"
            else -> localizedMessage ?: ""
        }

    private fun Response<*>.toErrorString() = "${code()} : ${message()}"

    private val Throwable.isConnectionException: Boolean
        get() = this is ConnectException ||
                this is UnknownHostException ||
                this is NoRouteToHostException ||
                this.cause?.isConnectionException ?: false

    private val Response<*>.isCodeUnauthorized
        get() = code() == CODE_UNAUTHORIZED
}

val ResponseBody.message: String
    get() = try {
        JSONObject(string()).getString("message")
    } catch (e: Exception) {
        e.message.toString()
    }

object HttpStatusCode {
    const val CODE_UNAUTHORIZED = 401
}