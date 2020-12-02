package com.example.cryptoexample


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoexample.pojo.CryptoCurrency
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var retrofit: Retrofit
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var BASE_URL="https://api.cryptonator.com/api/full/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


        callEndpoints()
    }

    @SuppressLint("CheckResult")
    private fun callEndpoints() {
        val cryptoCurrencyService = retrofit.create(
            CryptoCurrencyService::class.java
        )

        val btcObservable: Observable<MutableList<CryptoCurrency>>? = cryptoCurrencyService.getCoinData("btc")
            .flatMap{ Observable.fromIterable(it.ticker?.markets)}
            .map{ CryptoCurrency(it.market,it.price,it.volume,"btc") }?.toList()?.toObservable()

        val ethObservable: Observable<MutableList<CryptoCurrency>>? = cryptoCurrencyService.getCoinData("eth")
            .flatMap{ Observable.fromIterable(it.ticker?.markets)}
            .map{ CryptoCurrency(it.market,it.price,it.volume,"eth") }?.toList()?.toObservable()

        Observable.merge(btcObservable, ethObservable)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResults, this::handleError)
    }

    private fun handleResults(currencies: List<CryptoCurrency>?) {
        if (currencies?.isNotEmpty()!!) {
            recyclerViewAdapter.setData(currencies)
        } else {
            Toast.makeText(
                this, "NO RESULTS FOUND",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(
            this, "ERROR IN FETCHING API RESPONSE. Try again",
            Toast.LENGTH_LONG
        ).show()
    }
}