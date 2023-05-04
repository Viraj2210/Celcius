package com.d2k.shg.networking

import com.celcius.celcius.base.CommanModel
import com.celcius.celcius.ui.model.OrderModel

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IApiService {

    /**
     * Authenticate call.
     *
     * @param loginRequest the login request Ex:- <String,String>
     * @return the call
     */
    /*IAuthenticate this API Call Interface Is use to Login*/
    @FormUrlEncoded
    @POST("api/rider/hyper-local/complete")
    suspend fun orderList(
        @Field("third_party_order_id") request : Int
    ): Response<CommanModel<List<OrderModel>>>
}