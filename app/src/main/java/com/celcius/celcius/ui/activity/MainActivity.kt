package com.celcius.celcius.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.celcius.celcius.R
import com.celcius.celcius.base.ViewModelFactory
import com.celcius.celcius.databinding.ActivityMainBinding
import com.celcius.celcius.ui.OrderVM
import com.celcius.celcius.ui.adapter.OrderAdapter
import com.celcius.celcius.ui.model.OrderModel
import com.d2k.losapp.util.Status
import com.d2k.shg.networking.ApiClient
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var orderVM: OrderVM
    var pgNo : Int = 1
    lateinit var list : MutableList<OrderModel>
    lateinit var orderAdapter : OrderAdapter
    lateinit var layoutManager : LinearLayoutManager
   // var map : Map<String,String> = HashMap<String,String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
        activityMainBinding.toolbar.setLogo(R.drawable.ic_delivery)


       init()
       observer()
       listener()
    }

    fun init(){
        orderVM = ViewModelProvider(this,
            ViewModelFactory(ApiClient.aPIService)
        ).get(OrderVM::class.java)

        list = mutableListOf()


        //val map = mapOf("third_party_order_id" to 1)
        activityMainBinding.rcList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        activityMainBinding.rcList.layoutManager = layoutManager


        orderAdapter = OrderAdapter(
            this@MainActivity,
        )
        activityMainBinding.rcList.adapter = orderAdapter

    }


    fun observer(){
        lifecycleScope.launchWhenCreated {
            orderVM.orderListFlow.collectLatest {
                when(it.status){
                    Status.SUCCESS->{
                        if (it.data?.data?.get(0)!=null) {
                            list.addAll(it.data.data)
                            orderAdapter.updatedList(list)
                            activityMainBinding.tvError.visibility = View.GONE
                        }else{
                            activityMainBinding.tvError.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR->{

                    }
                    Status.LOADING->{

                    }
                }
            }
        }
    }

    fun listener(){
        activityMainBinding.rcList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    orderVM.getOrderList(pgNo++)
                }
            }
        })

        activityMainBinding.btnPending.setOnClickListener {
            activityMainBinding.tvError.text = "No Pending order list found"
        }

        activityMainBinding.btnRunning.setOnClickListener {
            activityMainBinding.tvError.text = "No Running order list found"
        }

        activityMainBinding.btnCompleted.setOnClickListener {
            orderVM.getOrderList(pgNo)
            //activityMainBinding.tvError.visibility = View.GONE
        }


        activityMainBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                orderVM.getOrderList(newText?.toInt()!!)
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}