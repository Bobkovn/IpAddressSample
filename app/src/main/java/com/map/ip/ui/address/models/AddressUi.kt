package com.map.ip.ui.address.models


data class AddressUi(
    val country: String,
    val regionName: String,
    val city: String,
    val zip: String,
    val latitude: Double,
    val longitude: Double,
) {
    override fun toString(): String {
        return "Страна: $country \n" +
                "Область: $regionName \n" +
                "Город: $city \n" +
                "Почтовый индекс: $zip \n" +
                "$latitude, $longitude"
    }
}