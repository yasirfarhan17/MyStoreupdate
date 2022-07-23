package com.example.mystoreupdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RemovePinCode extends AppCompatActivity {

    Spinner spinner;
    Button on,off,back;
    DatabaseReference ref;
ArrayList<String> arr=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_pin_code);
        spinner=findViewById(R.id.RSpinner);
        on=findViewById(R.id.ButtonOn);
        off=findViewById(R.id.Buttonoff);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ref= FirebaseDatabase.getInstance().getReference("PinCode");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr.clear();
                arr.add("Choose PinCode");
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String str=dataSnapshot1.getKey().toString();
                    arr.add(str);

                }
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arr);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);
                spinner.setSelection(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.getSelectedItemPosition()==0){
                    Toast.makeText(RemovePinCode.this,"Please select pin Code",Toast.LENGTH_SHORT).show();
                }
                else {
                    ref = FirebaseDatabase.getInstance().getReference("PinCode").child(spinner.getSelectedItem().toString()).child("status");
                    ref.setValue("on");
                    Toast.makeText(RemovePinCode.this, "on Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.getSelectedItemPosition()==0){
                    Toast.makeText(RemovePinCode.this,"Please select pin Code",Toast.LENGTH_SHORT).show();
                }
                else {
                    ref = FirebaseDatabase.getInstance().getReference("PinCode").child(spinner.getSelectedItem().toString()).child("status");
                    ref.setValue("off");
                    Toast.makeText(RemovePinCode.this, "off Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
