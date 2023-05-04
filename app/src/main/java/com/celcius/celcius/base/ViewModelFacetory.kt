package com.celcius.celcius.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.celcius.celcius.ui.OrderRepo
import com.celcius.celcius.ui.OrderVM
import com.d2k.shg.networking.IApiService


class ViewModelFactory(private val apiService: IApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(OrderVM::class.java)) {
            return OrderVM(OrderRepo(apiService)) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}