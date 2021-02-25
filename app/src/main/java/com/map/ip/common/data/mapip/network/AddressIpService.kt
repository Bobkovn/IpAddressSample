package com.map.ip.common.data.mapip.network

import com.map.ip.common.data.mapip.model.*
import retrofit2.Call
import retrofit2.http.*


const val JSON_PATH = "json/"

interface AddressIpService {

    @GET("$JSON_PATH{ip}")
    fun getAddressByIp(@Path("ip") ip: String): Call<IpAddress>

}
