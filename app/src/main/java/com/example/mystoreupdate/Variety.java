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

import com.example.mystoreupdate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Variety extends AppCompatActivity {
    Spinner cat,pro,var;
    EditText price;
    Button submit,back;
    DatabaseReference ref;
    ArrayList<String> arr=new ArrayList<>();
    ArrayList<String> arr1=new ArrayList<>();
    String [] arr2={"Choose Weight","250gm","500gm"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variety);
        cat=(Spinner)findViewById(R.id.catSpinner);
        var=findViewById(R.id.varSpinner);
        pro=findViewById(R.id.proSpinner);
        price=findViewById(R.id.price);
        submit=findViewById(R.id.submit);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ref= FirebaseDatabase.getInstance().getReference("Category");
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

                                ArrayAdapter<String> adapter1 =
                                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, arr2);
                                adapter1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                var.setAdapter(adapter1);
                                var.setSelection(0);





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
                if(price.getText().toString().isEmpty()||pro.getSelectedItem()==null||cat.getSelectedItem()==null||pro.getSelectedItemPosition()==0||cat.getSelectedItemPosition()==0){
                    Toast.makeText(Variety.this,"Enter correct details",Toast.LENGTH_SHORT).show();
                }
                else{

                    String str=var.getSelectedItem().toString();
                    String str1=pro.getSelectedItem().toString();
                    varietyData ob=new varietyData(price.getText().toString(),str);
                    ref=FirebaseDatabase.getInstance().getReference("variety").child(str1).child(str);
                    ref.setValue(ob);
                    Toast.makeText(Variety.this,"Successfully added",Toast.LENGTH_SHORT).show();

                }
            }
        });




    }
}
