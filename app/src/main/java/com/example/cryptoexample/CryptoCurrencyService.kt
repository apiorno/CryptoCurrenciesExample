package com.example.cryptoexample

import com.example.cryptoexample.pojo.CryptoResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoCurrencyService {

    @GET("{coin}-usd")
    fun getCoinData(@Path("coin") coin: String): Observable<CryptoResult>
}