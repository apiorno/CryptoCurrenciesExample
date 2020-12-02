package com.example.cryptoexample.pojo

import com.google.gson.annotations.SerializedName

class Market {
    @SerializedName("market")
    var market: String? = null

    @SerializedName("price")
    var price: String? = null

    @SerializedName("volume")
    var volume: Float? = null

}