package com.example.mystoreupdate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mystoreupdate.databinding.ActivityOrderNewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderNewActivity : AppCompatActivity(), MyOrderCallBack {


    private lateinit var binding: ActivityOrderNewBinding

    private var _checkoutOrder = MutableLiveData<ArrayList<checkOutModel>>()
    val checkoutOrder: LiveData<ArrayList<checkOutModel>> = _checkoutOrder
    private var _orderId = MutableLiveData<ArrayList<String>>()
    val orderId: LiveData<ArrayList<String>> = _orderId
    val ref = FirebaseDatabase.getInstance().getReference("orderNew")
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_new)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_new)
        getInit()
        getOrder("")//Todo
        addObserver()
    }

    private fun addObserver() {
        checkoutOrder.observe(this) {

        }
        orderId.observe(this) {

        }


    }


    fun getInit() {
        with(binding) {
            rvNewOrders.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            rvNewOrders.adapter = NewOrderAdapter(this@OrderNewActivity)
        }
    }


    fun getOrder(key: String) {
        Log.d("insideMyOrder", "$key")
        ref.child(key).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list = ArrayList<checkOutModel>()
                    snapshot.children.forEach {
                        val item: checkOutModel = it.getValue(checkOutModel::class.java)!!
                        list.add(item)
                    }
                    Log.d("insideMyOrder", "$snapshot $list")
                    _checkoutOrder.postValue(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })

    }

    override fun onItemClick(orderId: String) {
        //TODO
    }
}