package com.example.mystoreupdate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.Dispatcher
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderHomecheck : AppCompatActivity(){


    lateinit var ref: DatabaseReference
    lateinit var preferences: SharedPreferences
    lateinit var preferences1:SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var back: Button
    lateinit var key: String
    val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    val currentTime = SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(Date())
    var arr = ArrayList<String>()
    var arr1 = ArrayList<String>()
    var recyclerView1: RecyclerView? = null

    var adapter: MyOrderAdapter? = null
    var p = 0
    var i:Int = 0
    var a:Int = 0
    var b:Int = 0
    var vl = ArrayList<Int>()
    var kyy: String? = null
    val hashMap = HashMap<String, ArrayList<String>>()


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("key", key)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_home)

        preferences1 = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences1.edit()
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        key = preferences.getString("key", "").toString()
        back = findViewById(R.id.back)
        recyclerView1 = findViewById(R.id.cart_recyclerView1)


        //t3=findViewById(R.id.totalPrice);
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView1!!.layoutManager = linearLayoutManager

        ref = FirebaseDatabase.getInstance().getReference("Myorder")

        allFun()

        back.setOnClickListener { onBackPressed() }

    }

    fun allFun() {
        val waitingtDialog = SpotsDialog.Builder().setContext(this).build()
        waitingtDialog.show()

        //GlobalScope.launch(Dispatchers.IO) {
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                val val2 = dataSnapshot.key.toString()
                val p = intArrayOf(0)
                arr.add(val2)
                Log.d("kyy", val2)


                // Toast.makeText(OrderHome.this,""+arr,Toast.LENGTH_SHORT).show();
                i = 0
                while (i < arr.size) {

                    // Toast.makeText(OrderHome.this,""+arr.get(i),Toast.LENGTH_SHORT).show();
                    p[0] = i
                    val d1 = arr[i]
                    ref = FirebaseDatabase.getInstance().getReference("Myorder").child(arr[i])
                        .child("Item").child("YourOrder")
                    ref.addChildEventListener(object : ChildEventListener {
                        override fun onChildAdded(dataSnapshot1: DataSnapshot, s: String?) {
                            val val1 = dataSnapshot1.key
                            Log.d("check", "" + arr)
                            if (hashMap.containsKey(d1)) {
                                val x: ArrayList<String>?
                                x = hashMap[d1]
                                assert(x != null)
                                if (!x!!.contains(val1!!)) {
                                    x.add(val1)
                                }
                                hashMap[d1] = x
                            } else {
                                var x: ArrayList<String>? = ArrayList()
                                if (!x!!.contains(val2)) {
                                    if (val1 != null) {
                                        x.add(val1)
                                    }
                                }
                                hashMap[d1] = x
                                x = null
                            }
                            if (!arr1.contains(val1)) {
                                arr1.add(val1!!)
                            }
                            //GlobalScope.launch(Dispatchers.Main) {
                            Collections.sort(arr1)
                            waitingtDialog.dismiss()
                            Log.d("check1", "" + hashMap)
                            //deleteTask()
                           // }
                            fun1(arr1,hashMap)
                        }

                        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                        override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                    i++
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
        }
   // }

//    private fun deleteTask() {
//      //  GlobalScope.launch(Dispatchers.IO) {
//            val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
//                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//                    override fun onMove(
//                        recyclerView: RecyclerView,
//                        viewHolder: ViewHolder,
//                        target: ViewHolder
//                    ): Boolean {
//                        return false
//                    }
//
//                    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
//
//                        val alertDialogBuilder = AlertDialog.Builder(applicationContext)
//
//                        // set title
//                        alertDialogBuilder.setTitle("Delete")
//
//                        // set dialog message
//                        alertDialogBuilder
//                            .setMessage("Do you really want to delete it?")
//                            .setCancelable(false)
//                            .setPositiveButton("Yes") { dialog, id ->
//                                Log.d("qwe", "" + hashMap)
//                                for ((str, ar) in hashMap) {
//                                    if (ar.contains(arr1[viewHolder.position])) {
//                                        ref = FirebaseDatabase.getInstance().getReference("Myorder")
//                                            .child(str)
//                                            .child("Item").child("YourOrder")
//                                        ref.child(arr1[viewHolder.position]).removeValue()
//                                        Toast.makeText(
//                                            applicationContext,
//                                            "Removed Successfully",
//                                            Toast.LENGTH_LONG
//                                        )
//                                            .show()
//                                    }
//                                }
//                                arr1.removeAt(viewHolder.position)
//                                adapter!!.notifyDataSetChanged()
//                            }
//                            .setNegativeButton(
//                                "No"
//                            ) { dialog, id -> //arr1.add(viewHolder.getPosition(),arr1.get(viewHolder.getPosition()));
//                                adapter!!.notifyDataSetChanged()
//                                dialog.cancel()
//                            }
//
//                        // create alert dialog
//                        val alertDialog = alertDialogBuilder.create()
//
//                        // show it
//                        alertDialog.show()
//                    }
//                }
//
//
//            fun1(arr1)
//        //}
//
//
//    }
    //, itemTouchHelperCallback: ItemTouchHelper.Callback?
    fun fun1(arr1: ArrayList<String> , hashMap: HashMap<String,ArrayList<String>>) {
       // GlobalScope.launch(Dispatchers.Default) {
        Log.d("Before sort", "" + arr1)
        //Toast.makeText(OrderHome.this,""+arr1,Toast.LENGTH_LONG).show();
        //Collections.reverse(arr1);
        Log.d("After sort", "" + arr1)
        adapter = MyOrderAdapter(arr1, this@OrderHomecheck, arr,hashMap)
        //ItemTouchHelper(itemTouchHelperCallback!!).attachToRecyclerView(recyclerView1)
        adapter!!.notifyDataSetChanged()
        recyclerView1!!.adapter = adapter
   // }
    }
}