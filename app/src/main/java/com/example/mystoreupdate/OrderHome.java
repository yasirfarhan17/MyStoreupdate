//package com.example.mystoreupdate;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import dmax.dialog.SpotsDialog;
//
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.View;
//import android.widget.Adapter;
//import android.widget.Button;
//import android.widget.GridLayout;
//import android.widget.ImageView;
//import android.widget.SearchView;
//import android.widget.Toast;
//
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.lang.reflect.Array;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//
//public class OrderHome extends AppCompatActivity {
//    DatabaseReference ref;
//    SharedPreferences preferences,preferences1;
//    SharedPreferences.Editor editor ;
//    Button back;
//    String key,val,ad;
//    final String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//    final String currentTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());
//    ArrayList<String> arr=new ArrayList<>();
//    ArrayList<String> arr1=new ArrayList<>();
//    RecyclerView recyclerView1;
//
//    MyOrderAdapter adapter;
//    int p=0,i=0,a=0,b=0;
//    ArrayList<Integer>  vl=new ArrayList<>();
//    String kyy;
//    final HashMap<String, ArrayList<String>> hashMap = new HashMap<String,ArrayList<String>>();
//
//
//
//
//
//    @Override
//    public void onBackPressed() {
//        Intent intent=new Intent(this,MainActivity.class);
//        intent.putExtra("key",key);
//        startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_order_home);
//        preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
//        editor = preferences1.edit();
//        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        key = preferences.getString("key", "");
//        back = findViewById(R.id.back);
//
//
//
//        onSearchRequested();
//
//        recyclerView1 = findViewById(R.id.cart_recyclerView1);
//
//
//        //t3=findViewById(R.id.totalPrice);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
//        recyclerView1.setLayoutManager(linearLayoutManager);
//
//
//
//        ref = FirebaseDatabase.getInstance().getReference("Myorder");
//
//        allFun();
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//
//            }
//        });
//
//
//    }
//
//    void fun(ArrayList<String> arr1,ItemTouchHelper.Callback itemTouchHelperCallback) {
//            Log.d("Before sort", "" + arr1);
//            //Toast.makeText(OrderHome.this,""+arr1,Toast.LENGTH_LONG).show();
//            //Collections.reverse(arr1);
//            Log.d("After sort", "" + arr1);
//
//            adapter = new MyOrderAdapter(arr1, OrderHome.this, arr);
//
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView1);
//            adapter.notifyDataSetChanged();
//            recyclerView1.setAdapter(adapter);
//        }
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void show_Notification(String title, String body){
//
//        Intent intent=new Intent();
//        String CHANNEL_ID="MYCHANNEL";
//        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
//        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
//        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
//                .setContentText(body)
//                .setContentTitle(title)
//                .setContentIntent(pendingIntent)
//                .addAction(android.R.drawable.sym_action_chat,title,pendingIntent)
//                .setChannelId(CHANNEL_ID)
//                .setSmallIcon(android.R.drawable.sym_action_chat)
//                .build();
//
//        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.createNotificationChannel(notificationChannel);
//        notificationManager.notify(1,notification);
//
//
//    }
//
//    public  void search(String str) {
//        ArrayList<String> arr4=new ArrayList<>();
//
//        for (String obj : arr) {
//            if (obj.toLowerCase().contains(str.toLowerCase())) {
//                arr4.add(obj);
//
//            }
//            adapter = new MyOrderAdapter(arr4, OrderHome.this, arr1);
//            adapter.notifyDataSetChanged();
//            recyclerView1.setAdapter(adapter);
//
//
//            //handler.removeCallbacks(check);
//
//
//        }
//
//    }
//
//
//    void allFun(){
//        final android.app.AlertDialog waitingtDialog = new SpotsDialog.Builder().setContext(OrderHome.this).build();
//        waitingtDialog.show();
//
//
//        ref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                final String val = dataSnapshot.getKey().toString();
//                final int[] p = {0};
//                arr.add(val);
//                Log.d("kyy",val);
//
//
//
//
//
//                // Toast.makeText(OrderHome.this,""+arr,Toast.LENGTH_SHORT).show();
//
//
//                for ( i = 0; i < arr.size(); i++) {
//                    // Toast.makeText(OrderHome.this,""+arr.get(i),Toast.LENGTH_SHORT).show();
//                    p[0] =i;
//                    final String d1=arr.get(i);
//
//                    ref = FirebaseDatabase.getInstance().getReference("Myorder").child(arr.get(i)).child("Item").child("YourOrder");
//                    ref.addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull final DataSnapshot dataSnapshot1, @Nullable String s) {
//                            String val1 = dataSnapshot1.getKey();
//                            Log.d("check",""+arr);
//                            if(hashMap.containsKey(d1)){
//                                ArrayList<String> x;
//                                x=hashMap.get(d1);
//                                assert x != null;
//                                if(!x.contains(val1)) {
//                                    x.add(val1);
//                                }
//                                hashMap.put(d1,x);
//                            }
//                            else{
//                                ArrayList<String> x=new ArrayList<>();
//                                if(!x.contains(val)) {
//                                    x.add(val1);
//                                }
//                                hashMap.put(d1,x);
//                                x=null;
//                            }
//
//                            if (!arr1.contains(val1)) {
//                                arr1.add(val1);
//
//
//                            }
//                            Collections.sort(arr1);
//                            waitingtDialog.dismiss();
//                            Log.d("check1",""+hashMap);
//                            deleteTask();
//
//
//
//
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void deleteTask() {
//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        OrderHome.this);
//
//                // set title
//                alertDialogBuilder.setTitle("Delete");
//
//                // set dialog message
//                alertDialogBuilder
//                        .setMessage("Do you really want to delete it?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//
//                                Log.d("qwe",""+hashMap);
//                                for(HashMap.Entry<String, ArrayList<String>> hashmap_data : hashMap.entrySet()){
//                                    String str=hashmap_data.getKey();
//                                    ArrayList<String> ar=hashmap_data.getValue();
//                                    if(ar.contains(arr1.get(viewHolder.getPosition()))){
//                                        ref = FirebaseDatabase.getInstance().getReference("Myorder").child(str).child("Item").child("YourOrder");
//                                        ref.child(arr1.get(viewHolder.getPosition())).removeValue();
//
//                                        Toast.makeText(OrderHome.this,"Removed Successfully",Toast.LENGTH_LONG).show();
//
//                                    }
//                                }
//
//                                arr1.remove(viewHolder.getPosition());
//                                adapter.notifyDataSetChanged();
//
//
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //arr1.add(viewHolder.getPosition(),arr1.get(viewHolder.getPosition()));
//                                adapter.notifyDataSetChanged();
//                                dialog.cancel();
//                            }
//                        });
//
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();
//
//
//
//
//            }
//        };
//        fun(arr1,itemTouchHelperCallback);
//    }
//
//
//}
//
