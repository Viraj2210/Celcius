package com.celcius.celcius.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.celcius.celcius.databinding.AdapterProductListBinding

import com.celcius.celcius.ui.model.Product

class ProductListAdapter(var context : Context,var list : List<Product> ) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    var viewMore = false
    class ViewHolder(val adapterProductListBinding: AdapterProductListBinding)
        : RecyclerView.ViewHolder(adapterProductListBinding.root){

            fun bind(product: Product){
                adapterProductListBinding.apply {
                    tvProductName.text = product.product_name
                    tvQty.text = product.product_qty.toString()
                    tvBox.text = product.boxes_required_per_item.toString()
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterProductListBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position))
    }

    fun viewMore(viewMore : Boolean) {
        this.viewMore = viewMore
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (!viewMore && list.size>2) {
            return 2
        }else {
            return list.size
        }
    }
}