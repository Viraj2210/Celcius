package com.celcius.celcius.ui

import com.celcius.celcius.base.CommanModel
import com.celcius.celcius.ui.model.OrderModel
import com.d2k.losapp.util.BaseDataSource
import com.d2k.losapp.util.Resources
import com.d2k.shg.networking.IApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class OrderRepo(var apiService: IApiService) : BaseDataSource() {


    suspend fun getOrderList(request : Int) :
            Flow<Resources<CommanModel<List<OrderModel>>>>{
            return flow {
                val result = safeApiCall { apiService.orderList(request) }
                emit(result)
            }.flowOn(Dispatchers.IO)
    }

}