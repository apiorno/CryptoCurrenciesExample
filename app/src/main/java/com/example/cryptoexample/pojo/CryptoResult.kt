package com.example.cryptoexample.pojo

import com.google.gson.annotations.SerializedName

class CryptoResult {
    @SerializedName("ticker")
    var ticker: Ticker? = null

    @SerializedName("timestamp")
    var timestamp: Int? = null

    @SerializedName("success")
    var success: Boolean? = null

    @SerializedName("error")
    var error: String? = null
}