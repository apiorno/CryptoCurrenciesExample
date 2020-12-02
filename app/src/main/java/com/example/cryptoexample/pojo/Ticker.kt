package com.example.cryptoexample.pojo

import com.google.gson.annotations.SerializedName

class Ticker {

    @SerializedName("base")
    var base: String? = null

    @SerializedName("target")
    var target: String? = null

    @SerializedName("price")
    var price: String? = null

    @SerializedName("volume")
    var volume: String? = null

    @SerializedName("change")
    var change: String? = null

    @SerializedName("markets")
    var markets: List<Market>? = null
}