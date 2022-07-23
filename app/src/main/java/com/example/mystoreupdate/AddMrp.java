package com.example.mystoreupdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddMrp extends AppCompatActivity {
    Spinner cat,pro;
    TextView mrp;
    Button button,back;
    DatabaseReference ref;
    ArrayList<String> arr=new ArrayList<>();
    ArrayList<String> arr1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mrp);
        cat=(Spinner)findViewById(R.id.catSpinner);
        pro=findViewById(R.id.proSpinner);
        button = findViewById(R.id.removebtn);
        back=findViewById(R.id.back);
        mrp=findViewById(R.id.mrp);
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
                    Toast.makeText(AddMrp.this, "Enter Product Name", Toast.LENGTH_SHORT).show();

                } else {

                    String str1=pro.getSelectedItem().toString();
                    ref=FirebaseDatabase.getInstance().getReference("Add Mrp");
                    ref.child(str1).setValue(mrp.getText().toString());
                    cat.setSelection(0);
                    pro.setSelection(0);
                    mrp.setText("");

                }

    }
});
    }
}

