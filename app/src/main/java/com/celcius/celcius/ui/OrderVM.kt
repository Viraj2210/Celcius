package com.celcius.celcius.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celcius.celcius.base.CommanModel
import com.celcius.celcius.ui.model.OrderModel
import com.d2k.losapp.util.Resources
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class OrderVM(val repo : OrderRepo) :ViewModel(){
    private val _orderListFlow = Channel<Resources<CommanModel<List<OrderModel>>>>(Channel.BUFFERED)
    val orderListFlow =  _orderListFlow.receiveAsFlow()



    fun getOrderList(request : Int){
        viewModelScope.launch {
            repo.getOrderList(request = request).catch {
                _orderListFlow.send(Resources.error(null,it.message?: "Error Occured"))
            }.collect{
                _orderListFlow.send(it)
            }
        }
    }
}