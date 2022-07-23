package com.example.mystoreupdate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class product1 extends AppCompatActivity {
    Button add,remove,change,back,var,stock1,RemoveVar,mrp,RemoveMrp,versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product1);
        add=findViewById(R.id.addButton);
        remove=findViewById(R.id.RemoveButton);
        change=findViewById(R.id.ChangeButton);
        var=findViewById(R.id.VarietyButton);
        RemoveVar=findViewById(R.id.RemoveVarity);
        stock1=(Button)findViewById(R.id.sdk);
        mrp=findViewById(R.id.AddMrpp);
        RemoveMrp=findViewById(R.id.RemoveMrpp);
        versionCode=findViewById(R.id.versionCode);

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
                Intent intent =new Intent(product1.this,product.class);
                startActivity(intent);
            }
        });
        stock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(product1.this,stock.class);
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(product1.this,productRemove.class);
                startActivity(intent);
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(product1.this,changePrice.class);
                startActivity(intent);
            }
        });
        var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(product1.this,Variety.class);
                startActivity(intent);
            }
        });
        RemoveVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(product1.this,RemoveVariety.class);
                startActivity(intent);
            }
        });
        mrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(product1.this,AddMrp.class);
                startActivity(intent);
            }
        });
        RemoveMrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(product1.this, com.example.mystoreupdate.RemoveMrp.class);
                startActivity(intent);
            }
        });
        versionCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(product1.this, com.example.mystoreupdate.versionCode.class);
                startActivity(intent);
            }
        });
    }
}
