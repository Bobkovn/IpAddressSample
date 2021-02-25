package com.map.ip.data.address.model

import com.google.gson.annotations.SerializedName


data class IpAddress(
    @SerializedName("query")
    val ip: String,
    val status: String,
    val country: String,
    val region: String,
    val regionName: String,
    val city: String,
    val zip: String,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    val timezone: String,
    val isp: String,
    @SerializedName("org")
    val organization: String,
    @SerializedName("as")
    val autonomousSystem : String
)