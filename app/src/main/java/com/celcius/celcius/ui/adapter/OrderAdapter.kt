package com.celcius.celcius.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.celcius.celcius.R
import com.celcius.celcius.databinding.AdapterOrderListBinding
import com.celcius.celcius.ui.model.OrderModel
import com.celcius.celcius.ui.model.Product

class OrderAdapter(val context: Context) : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    lateinit var productListAdapter : ProductListAdapter
    var selectedPosition : Int = -1
    val list: MutableList<OrderModel> = mutableListOf()

    class ViewHolder(val adapterOrderListBinding: AdapterOrderListBinding) :
        RecyclerView.ViewHolder(adapterOrderListBinding.root){

            fun bind(orderListModel: OrderModel,position: Int,context: Context){
                adapterOrderListBinding.apply {
                                  }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterOrderListBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position),position,context)
        var orderListModel = list.get(position)

        if (selectedPosition == position) {
            holder.adapterOrderListBinding.deliverDetails.visibility = View.VISIBLE
            holder.adapterOrderListBinding.constOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
            holder.adapterOrderListBinding.tvOrder.setTextColor(ContextCompat.getColor(context,R.color.white))
            holder.adapterOrderListBinding.tvTotalBox.setTextColor(ContextCompat.getColor(context,R.color.white))
           // holder.adapterOrderListBinding.deliverDetails.visibility = View.VISIBLE
            //holder.adapterOrderListBinding.linearProduct.visibility = View.VISIBLE
        }else{
            holder.adapterOrderListBinding.deliverDetails.visibility = View.GONE
            holder.adapterOrderListBinding.constOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            holder.adapterOrderListBinding.tvOrder.setTextColor(ContextCompat.getColor(context,R.color.black))
            holder.adapterOrderListBinding.tvTotalBox.setTextColor(ContextCompat.getColor(context,R.color.black))
            //holder.adapterOrderListBinding.linearProduct.visibility = View.GONE
        }

        holder.adapterOrderListBinding.tvOrder.text = "Order Id"+orderListModel.celcius_order_id.toString()
        holder.adapterOrderListBinding.tvTotalBox.text = "(Total Box = "+orderListModel.total_box.toString()+")"
        holder.adapterOrderListBinding.tvDateTime.text = orderListModel.pickup_date_time
        holder.adapterOrderListBinding.tvName.text = orderListModel.customer_name
        holder.adapterOrderListBinding.tvAddress.text = orderListModel.pickup_full_addr
        holder.adapterOrderListBinding.tvMobileNo.text = "Call: "+orderListModel.customer_mobile_no
        holder.adapterOrderListBinding.tvMobileNo2.text = "Call: "+orderListModel.support_mobile_no

        //holder.adapterOrderListBinding.linearProduct.visibility = View.GONE
        holder.adapterOrderListBinding.rcProductList.setHasFixedSize(true)
        holder.adapterOrderListBinding.rcProductList.layoutManager = LinearLayoutManager(context)
        productListAdapter = ProductListAdapter(context,orderListModel.product_list)
        holder.adapterOrderListBinding.rcProductList.adapter = productListAdapter
        productListAdapter.viewMore(false)

        holder.adapterOrderListBinding.constOrder.setOnClickListener {
            if (selectedPosition != position){
            //selectedPosition = holder.absoluteAdapterPosition
            holder.adapterOrderListBinding.constOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
            holder.adapterOrderListBinding.tvOrder.setTextColor(ContextCompat.getColor(context,R.color.white))
            holder.adapterOrderListBinding.tvTotalBox.setTextColor(ContextCompat.getColor(context,R.color.white))
            holder.adapterOrderListBinding.deliverDetails.visibility = View.VISIBLE
                //holder.adapterOrderListBinding.linearProduct.visibility = View.VISIBLE
                selectedPosition = position
                notifyItemChanged(position)
                notifyDataSetChanged()
            }else{
                holder.adapterOrderListBinding.deliverDetails.visibility = View.GONE
                holder.adapterOrderListBinding.constOrder.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                holder.adapterOrderListBinding.tvOrder.setTextColor(ContextCompat.getColor(context,R.color.black))
                holder.adapterOrderListBinding.tvTotalBox.setTextColor(ContextCompat.getColor(context,R.color.black))
                //holder.adapterOrderListBinding.linearProduct.visibility = View.GONE
                notifyItemChanged(position)
                notifyDataSetChanged()
            }
        }

        holder.adapterOrderListBinding.tvViewMore.setOnClickListener {
           // if (selectedPosition == position) {
                productListAdapter.viewMore(true)
            //}
        }
    }

    fun updatedList(updatedList: MutableList<OrderModel>){
        list.addAll(updatedList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}