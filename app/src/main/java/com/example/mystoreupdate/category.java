package com.example.mystoreupdate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class category extends AppCompatActivity {

    Button add,remove,back,addPin,removePin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        add=findViewById(R.id.addButton);
        remove=findViewById(R.id.RemoveButton);
        addPin=findViewById(R.id.addpin);
        removePin=findViewById(R.id.removepin);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(category.this,categoryAdd.class);
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(category.this,categoryRemove.class);
                startActivity(intent);
            }
        });

        addPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(category.this,pinCodeAdd.class);
                startActivity(intent);
            }
        });
        removePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(category.this,RemovePinCode.class);
                startActivity(intent);
            }
        });
    }
}
