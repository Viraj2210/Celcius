package com.d2k.shg.networking

import android.content.Context
import com.celcius.celcius.BuildConfig
import com.celcius.celcius.utils.Constants
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ApiClient {

    const val BASE_URL = "https://3cd7-2405-204-8480-d548-b0ea-10d7-5c75-ecfe.ngrok-free.app/"
//            const val UAT_URL = "http://219.90.67.69:8011/SHG_API/"

        var TOKEN: String? = null
        val aPIService: IApiService
            get() {
            val retrofit = Retrofit.Builder()
            retrofit
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(customLogInterceptor())
                .build()
            return retrofit.build().create(IApiService::class.java)
        }

    fun customLogInterceptor(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(CustomHttpLogging())
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .connectionSpecs(
                    Arrays.asList(
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.CLEARTEXT
                    )
                )
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .addInterceptor{
                    chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            it.addHeader("Accept","application/json")
                            it.addHeader("Authorization",Constants.TOKEN)
                        }.build())
                }.also {
                client->
                    client.addInterceptor(loggingInterceptor)
                }
                .build()
        } else {
            OkHttpClient.Builder()
                .connectionSpecs(
                    Arrays.asList(
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.CLEARTEXT
                    )
                )
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build()
        }
    }


}
