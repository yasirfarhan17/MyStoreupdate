package com.example.mystoreupdate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {


    public int total1;
    Handler handler = new Handler();
    ArrayList<String> product1 = new ArrayList<>();
    DatabaseReference ref;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String key;
    Context context;
    ArrayList<String> arr = new ArrayList<>();
    HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
    int item, index;


    public MyOrderAdapter() {

    }


    public MyOrderAdapter(ArrayList<String> p, Context c, ArrayList<String> arr, HashMap<String, ArrayList<String>> hashMap) {
        this.hashMap = hashMap;
        product1 = p;
        context = c;
        this.arr = arr;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderview, parent, false);
        MyViewHolder evh = new MyViewHolder(v);
        //view1=v;

        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        key = preferences.getString("key", "");


        holder.orderId.setText("Order Id: " + product1.get(position));


        for (int i = 0; i < arr.size(); i++) {
            hashMap.put(arr.get(i), product1);
            // holder.id.setText(arr.get(i));

            ref = FirebaseDatabase.getInstance().getReference("OrderDetail").child(arr.get(i)).child(product1.get(position)).child("Date");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String val = dataSnapshot.getValue().toString();
                        holder.date.setText("Delivery Date:" + val);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ref = FirebaseDatabase.getInstance().getReference("OrderDetail").child(arr.get(i)).child(product1.get(position)).child("CurrentDate");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String val = dataSnapshot.getValue().toString();
                        holder.currentDate.setText("Order Date:" + val);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ref = FirebaseDatabase.getInstance().getReference("OrderDetail").child(arr.get(i)).child(product1.get(position)).child("Payment");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String val = dataSnapshot.getValue().toString();
                        holder.t3.setText("Payment: " + val);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ref = FirebaseDatabase.getInstance().getReference("Myorder").child(arr.get(i)).child("Item").child("YourOrder").child(product1.get(position));
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        item = (int) dataSnapshot.getChildrenCount();

                        holder.t1.setText(item + " Items");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ref = FirebaseDatabase.getInstance().getReference("OrderDetail").child(arr.get(i)).child(product1.get(position)).child("Total");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String val = dataSnapshot.getValue().toString();
                        holder.t2.setText("Amount :₹ " + val);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ref = FirebaseDatabase.getInstance().getReference("OrderDetail").child(arr.get(i)).child(product1.get(position)).child("name");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String val = dataSnapshot.getValue().toString();
                        holder.orderName.setText("Name :" + val);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ref = FirebaseDatabase.getInstance().getReference("OrderConfirm").child(product1.get(position)).child("status");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String val = dataSnapshot.getValue().toString();
                        if (val.equals("no")) {
                            (holder.lay).setBackgroundColor(Color.parseColor("#41CEFC"));
                        } else if (val.equals("yes")) {
                            (holder.lay).setBackgroundColor(Color.parseColor("#FFFFFF"));
                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ref = FirebaseDatabase.getInstance().getReference("OrderDetail").child(arr.get(i)).child(product1.get(position)).child("phone");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String val1 = dataSnapshot.getValue().toString();
                        holder.orderPhone.setText("Phone :" + val1);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // Toast.makeText(v.getContext(),""+arr.get(finalI),Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < arr.size(); i++) {
                        ref = FirebaseDatabase.getInstance().getReference("OrderDetail").child(arr.get(i)).child(product1.get(position)).child("Time");
                        final int finalI = i;
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int index = finalI;
                                if (dataSnapshot.exists()) {
                                    String val = dataSnapshot.getValue()
                                            .toString();
                                    if (val.equals(product1.get(position))) {
                                        ref = FirebaseDatabase.getInstance().getReference("OrderDetail").child(arr.get(finalI)).child(product1.get(position)).child("status");
                                        ref.setValue("yes");

                                        Intent intent = new Intent(v.getContext(), LastPage.class);
                                        intent.putExtra("key", arr.get(finalI));
                                        intent.putExtra("OrderId", product1.get(position));
                                        v.getContext().startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    ref = FirebaseDatabase.getInstance().getReference("OrderConfirm").child(product1.get(position)).child("status");
                    ref.setValue("yes");


                /*Intent intent=new Intent(v.getContext(),LastPage.class);
                intent.putExtra("OrderId",product1.get(position));
                v.getContext().startActivity(intent);*/
                }
            });

            holder.dltButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(final View v) {
                    //delete();


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set title
                    alertDialogBuilder.setTitle("Delete");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Do you really want to delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    Log.d("qwe", "" + hashMap);
                                    for (HashMap.Entry<String, ArrayList<String>> hashmap_data : hashMap.entrySet()) {
                                        String str = hashmap_data.getKey();
                                        ArrayList<String> ar = hashmap_data.getValue();
                                        if (ar.contains(product1.get(position))) {
                                            ref = FirebaseDatabase.getInstance().getReference("Myorder").child(str).child("Item").child("YourOrder");
                                            ref.child(product1.get(position)).removeValue();

                                            Toast.makeText(context, "Removed Successfully", Toast.LENGTH_LONG).show();

                                        }
                                    }

                                    product1.remove(position);
                                    notifyDataSetChanged();
//

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //arr1.add(viewHolder.getPosition(),arr1.get(viewHolder.getPosition()));
                                    //adapter.notifyDataSetChanged();
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                }
            });


        }
        //Toast.makeText(context.getApplicationContext(),""+hashMap,Toast.LENGTH_SHORT).show();


    }


    @Override
    public int getItemCount() {
        return product1.size();
    }

    private void deleteTask(final int position) {


    }

    void delete() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("Exit");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you really want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context, "working", Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, date, currentDate, id, t1, t2, t3, orderName, orderPhone;
        ImageView img, img1;
        Button btnIns, btnDsc, addBtn, dltButton, order;
        LinearLayout lay;


        //final AdapterView.OnItemClickListener listener;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            orderId = (TextView) itemView.findViewById(R.id.orderIDDD);
            date = (TextView) itemView.findViewById(R.id.LDate);
            currentDate = (TextView) itemView.findViewById(R.id.CDate);
            t1 = (TextView) itemView.findViewById(R.id.orderItem);
            t2 = (TextView) itemView.findViewById(R.id.orderAmount);
            t3 = (TextView) itemView.findViewById(R.id.orderPayment);
            dltButton = (Button) itemView.findViewById(R.id.deletButton);
            orderName = itemView.findViewById(R.id.orderName);
            orderPhone = itemView.findViewById(R.id.orderPhone);
            lay = itemView.findViewById(R.id.lay);

        }

    }

}
