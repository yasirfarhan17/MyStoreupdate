package com.example.mystoreupdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RemoveVariety extends AppCompatActivity {
    Spinner cat,pro;
    Button submit,back;
    DatabaseReference ref;
    ArrayList<String> arr=new ArrayList<>();
    ArrayList<String> arr1=new ArrayList<>();
    String [] arr2={"Choose Weight","250gm","500gm"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_variety);
        cat=(Spinner)findViewById(R.id.catSpinner);
        pro=findViewById(R.id.proSpinner);
        submit=findViewById(R.id.submit);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ref= FirebaseDatabase.getInstance().getReference("variety");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr.clear();
                arr.add("Select Product");
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


                        ref=FirebaseDatabase.getInstance().getReference("variety").child(arr.get(position));
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                arr1.clear();
                                arr1.add("Select Quantity");
                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                    String val=dataSnapshot1.getKey();
                                    arr1.add(val);

                                }
                                ArrayAdapter<String> adapter =
                                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, arr1);
                                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                pro.setAdapter(adapter);
                                pro.setSelection(0);

                                ArrayAdapter<String> adapter1 =
                                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, arr2);
                                adapter1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pro.getSelectedItem()==null||cat.getSelectedItem()==null||pro.getSelectedItemPosition()==0||cat.getSelectedItemPosition()==0){
                    Toast.makeText(RemoveVariety.this,"Enter correct details",Toast.LENGTH_SHORT).show();
                }
                else{

                    String str=cat.getSelectedItem().toString();
                    String str1=pro.getSelectedItem().toString();
                    ref=FirebaseDatabase.getInstance().getReference("variety").child(str).child(str1);
                    ref.removeValue();
                    Toast.makeText(RemoveVariety.this,"Successfully Remove",Toast.LENGTH_SHORT).show();

                }
            }
        });




    }
}
