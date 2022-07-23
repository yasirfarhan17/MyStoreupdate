package com.example.mystoreupdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BannerDelete extends AppCompatActivity {
 RecyclerView recyclerView;
 DatabaseReference ref;
  ArrayList<BannerData> list=new ArrayList<>();
  BannerAdapter adapter;
  Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_delete);
        recyclerView=findViewById(R.id.Category_product);
        back=findViewById(R.id.back);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ref= FirebaseDatabase.getInstance().getReference("Banner");
        //Toast.makeText(getActivity(),"hii",Toast.LENGTH_SHORT).show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    BannerData p=dataSnapshot1.getValue(BannerData.class);
                    list.add(p);
                }

                adapter=new BannerAdapter(list,BannerDelete.this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BannerDelete.this,"Oppps....someThing is wrong", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
