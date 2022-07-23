package com.example.mystoreupdate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class productRemove extends AppCompatActivity {
    Spinner cat,pro;
    Button button,back;
    DatabaseReference ref;
    ArrayList<String> arr=new ArrayList<>();
    ArrayList<String> arr1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_remove);
        cat=(Spinner)findViewById(R.id.catSpinner);
        pro=findViewById(R.id.proSpinner);
        button = findViewById(R.id.removebtn);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Category");

        ref=FirebaseDatabase.getInstance().getReference("Category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr.clear();
                arr.add("Select Category");
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String val=dataSnapshot1.getKey();
                    arr.add(val);

                }
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, arr);
                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                cat.setAdapter(adapter);
                cat.setSelection(0);
                cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        // Toast.makeText(PaymentOption.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();


                        ref=FirebaseDatabase.getInstance().getReference("CategoryProducts").child(arr.get(position));
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                arr1.clear();
                                arr1.add("Select Product Name");
                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                    String val=dataSnapshot1.getKey();
                                    arr1.add(val);

                                }
                                ArrayAdapter<String> adapter =
                                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, arr1);
                                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                pro.setAdapter(adapter);
                                pro.setSelection(0);



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pro.getSelectedItem()==null||cat.getSelectedItem()==null||pro.getSelectedItemPosition()==0||cat.getSelectedItemPosition()==0) {
                    Toast.makeText(productRemove.this, "Enter Product Name", Toast.LENGTH_SHORT).show();

                } else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            productRemove.this);

                    // set title
                    alertDialogBuilder.setTitle("Exit");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Do you really want to delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String str=cat.getSelectedItem().toString();
                                    String str1=pro.getSelectedItem().toString();
                                    ref = FirebaseDatabase.getInstance().getReference("CategoryProducts").child(str);
                                    ref.child(str1).removeValue();
                                    Toast.makeText(productRemove.this, "Remove Successful", Toast.LENGTH_SHORT).show();
                                    cat.setSelection(0);
                                    pro.setSelection(0);
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

            }
        });
    }
}

