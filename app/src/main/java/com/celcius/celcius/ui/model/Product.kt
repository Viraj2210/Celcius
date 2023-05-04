package com.celcius.celcius.ui.model

data class Product(
    val boxes_required_per_item: Int,
    val created_at: String,
    val deleted: Int,
    val hyper_local_id: Int,
    val id: Int,
    val product_name: String,
    val product_qty: Int,
    val updated_at: String
)