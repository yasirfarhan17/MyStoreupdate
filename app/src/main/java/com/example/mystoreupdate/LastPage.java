package com.example.mystoreupdate;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dmax.dialog.SpotsDialog;

public class LastPage extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<Integer> arr=new ArrayList<>();
    ArrayList<String> Name=new ArrayList<>();
    ArrayList<Integer> arr1=new ArrayList<>();
    ArrayList<String> Name1=new ArrayList<>();
    ArrayList<lastData> m=new ArrayList<>();
    ArrayList<String> quant=new ArrayList<>();
    RecyclerView recyclerView;
    Dialog mydialog;
    lastAdapter adapter;
    ArrayList<String> data=new ArrayList<>();
    TextView finalTotal,add,lastPrice,lastQuant,fPrice,t4,orderId,date,t6;
    int a;
    String str=null,addd,val;
    SharedPreferences preferences;
    SharedPreferences.Editor editor ;
    static  int flag=0;
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    String currentTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());
    SharedPreferences preferences1;
    String key,str1,zz;
    ImageView qr;
    QRGEncoder qrgEncoder;
    Bitmap bitmap;
    ArrayList<String> va=new ArrayList<>();
    int size;
    int qqq=0;
    String add1,p;
    Button back;
    ImageView confirmed,onDeliver,delivered,Cconfirm,ConDelivery,Cdelivered;
    Button CB1,CB2,CB3;
    EditText check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_page);
        Name.clear();
        arr.clear();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //key = preferences.getString("key", "");


        str=getIntent().getStringExtra("add");
        str1=getIntent().getStringExtra("addd");
        add=findViewById(R.id.TvAddress);
        key=getIntent().getStringExtra("key");
        zz=getIntent().getStringExtra("OrderId");


        finalTotal=(TextView)findViewById(R.id.finalTotal);
        t4=(TextView)findViewById(R.id.paymentConfirm);
        orderId=(TextView)findViewById(R.id.ord);
        fPrice=(TextView)findViewById(R.id.fPrice);
        lastPrice=findViewById(R.id.lastPrice);
        lastQuant=findViewById(R.id.lastQuantity);
        qr=(ImageView)findViewById(R.id.qr);
        date=findViewById(R.id.DeliveryDate);
        back=findViewById(R.id.back);
        t6=findViewById(R.id.textView6);

        confirmed=(ImageView)findViewById(R.id.confirmed);
        onDeliver=(ImageView)findViewById(R.id.onDelivery);
        delivered=(ImageView)findViewById(R.id.delivered);
        //

        final android.app.AlertDialog waitingtDialog = new SpotsDialog.Builder().setContext(LastPage.this).build();
        waitingtDialog.show();

        recyclerView=findViewById(R.id.final_recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Toast.makeText(LastPage.this,zz,Toast.LENGTH_SHORT).show();

        orderId.setText("OrderId:-" +String.valueOf(zz));


        ref= FirebaseDatabase.getInstance().getReference("Myorder").child(key).child("Item").child("YourOrder").child(zz);
        m.clear();
        //Toast.makeText(LastPage.this,pp+" "+quant,Toast.LENGTH_SHORT).show();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String val=dataSnapshot.child("weight").getValue().toString();
                if(val.equals("Per Kg")||val.equals("per kg") ||val.equals("Per kg"))
                    val="1 Kg";

                lastData ob=new lastData(dataSnapshot.child("name").getValue().toString(),dataSnapshot.child("price").getValue().toString(),dataSnapshot.child("quant").getValue().toString(),val);
                m.add(ob);


                lastAdapter adapter=new lastAdapter(m);
                //adapter=new lastAdapter(Name1,arr1);

                recyclerView.setAdapter(adapter);


                a=0;
                code(m);
                size=m.size();
                waitingtDialog.dismiss();




                for(int i=0;i<m.size();i++){
                    //Toast.makeText(LastPage.this,""+arr1,Toast.LENGTH_SHORT).show();
                    a=a+(Integer.parseInt(m.get(i).getPrice())*Integer.parseInt(m.get(i).getQuant()));
                }
                finalTotal.setText(""+a);
                if(a>400 && a<=1000 ){
                    t6.setText(R.string.d1);
                    fPrice.setText("₹ "+(a+10));
                }
                else if(a>=100 && a<=400 ){
                    t6.setText(R.string.d);
                    fPrice.setText("₹ "+(a+20));
                }
                else if(a>50 && a<=100 ){
                    t6.setText(R.string.d3);
                    fPrice.setText("₹ "+(a+40));
                }
                else if(a<=50){
                    t6.setText(R.string.d4);
                    fPrice.setText("₹ "+(a+100));
                }

                else{
                    t6.setText(R.string.d2);
                    fPrice.setText("₹ "+(a));
                }


                preferences = PreferenceManager.getDefaultSharedPreferences(LastPage.this);
                editor = preferences.edit();
                editor.putInt("go", a);
                editor.apply();






                //Toast.makeText(LastPage.this,""+m,Toast.LENGTH_SHORT).show();


            }



            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        ref= FirebaseDatabase.getInstance().getReference("Myorder").child(key).child("add");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    addd = dataSnapshot.getValue().toString();
                    //Toast.makeText(LastPage.this, str + " " + addd, Toast.LENGTH_SHORT).show();
                    add.setText(addd);
                    editor.putString("add",addd);
                    editor.apply();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref= FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String val=dataSnapshot.child("Payment").getValue().toString();
                    String val1=dataSnapshot.child("Date").getValue().toString();
                    t4.setText(val);
                    date.setText("DeliveryDate: "+val1);
                    editor.putString("pay",val);
                    editor.apply();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });





        //Toast.makeText(this,""+arr+" "+Name,Toast.LENGTH_LONG).show();

        ref=FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz).child("confirmed");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    confirmed.setBackgroundResource(R.drawable.border1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref=FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz).child("OnDelivery");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    onDeliver.setBackgroundResource(R.drawable.border1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref=FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz).child("Delivered");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    delivered.setBackgroundResource(R.drawable.border1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref=FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz);
                ref.child("confirmed").setValue("ok");
                confirmed.setBackgroundResource(R.drawable.border1);
            }
        });

        onDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref=FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz);
                ref.child("OnDelivery").setValue("ok");
                onDeliver.setBackgroundResource(R.drawable.border1);

            }
        });

        delivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref=FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz);
                ref.child("Delivered").setValue("ok");
                delivered.setBackgroundResource(R.drawable.border1);

            }
        });




    }

   /* public void MyAlertdialog (final String  key,final  String zz) {


        mydialog = new Dialog(LastPage.this);
        mydialog.setContentView(R.layout.popup);

       CB1=(Button) mydialog.findViewById(R.id.confirmButton);
        CB2=(Button) mydialog.findViewById(R.id.onDeliveryButton);
        CB3=(Button) mydialog.findViewById(R.id.deliveredButton);
        Cconfirm=mydialog.findViewById(R.id.confirm);
        ConDelivery=mydialog.findViewById(R.id.onDelivery);
        Cdelivered=mydialog.findViewById(R.id.delivered);

        CB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref=FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz);
                ref.child("confirmed").setValue("ok");
                Cconfirm.setVisibility(View.VISIBLE);
            }
        });

        CB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref=FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz);
                ref.child("OnDelivery").setValue("ok");
                ConDelivery.setVisibility(View.VISIBLE);
            }
        });

        CB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref=FirebaseDatabase.getInstance().getReference("OrderDetail").child(key).child(zz);
                ref.child("Delivered").setValue("ok");
                Cdelivered.setVisibility(View.VISIBLE);
            }
        });


        mydialog.setTitle("New Appointment");
        //Toast.makeText(getApplicationContext(), "opened popoup", Toast.LENGTH_LONG).show();

        mydialog.show();

    }*/

    void code(ArrayList<lastData> val){
        preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        add1 = preferences1.getString("add", "");

        preferences1 = PreferenceManager.getDefaultSharedPreferences(this);
        p = preferences1.getString("pay", "");


        while(qqq<val.size()) {
            va.add("\nName= "+val.get(qqq).getName() + " ,Quantity= " + val.get(qqq).getPrice() + "x" + val.get(qqq).getQuant() + " ,Total=  ₹  " + Integer.parseInt(val.get(qqq).getPrice()) * Integer.parseInt(val.get(qqq).getQuant()) + "\n");
            Log.d("Check", "" + qqq);
            qqq++;
        }

        //Toast.makeText(LastPage.this,""+p,Toast.LENGTH_LONG).show();

        qrgEncoder = new QRGEncoder(("OrderId= "+zz+"\nItem="+va+"\n Address=  \""+add1+"\"\n Payment="+p), null, QRGContents.Type.TEXT, 2500);
        bitmap = qrgEncoder.getBitmap();

        // Getting QR-Code as Bitmap

        // Setting Bitmap to ImageView
        qr.setImageBitmap(bitmap);


    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LastPage.this,OrderHomecheck.class);
        startActivity(intent);
    }

}
