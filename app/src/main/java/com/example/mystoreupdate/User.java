package com.example.mystoreupdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User extends AppCompatActivity {
    DatabaseReference ref;
    RecyclerView recyclerView;
    userAdapter adapter;
    ArrayList<userData> arr=new ArrayList<>();
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerView=findViewById(R.id.category_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ref= FirebaseDatabase.getInstance().getReference("User");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("name").exists() && dataSnapshot1.child("phone").exists()){
                        String name=dataSnapshot1.child("name").getValue().toString();
                        String phone=dataSnapshot1.child("phone").getValue().toString();
                        String on="off";
                        if(dataSnapshot1.child("online").exists()){
                            on=dataSnapshot1.child("online").getValue().toString();
                        }
                        userData ob=new userData(name,phone,on);
                        arr.add(ob);
                    }

                }
                adapter=new userAdapter(arr);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
