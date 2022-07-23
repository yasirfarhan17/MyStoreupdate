package com.example.mystoreupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BannerHome extends AppCompatActivity {
    Button add,remove,back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_home);
        add=findViewById(R.id.add);
        back=findViewById(R.id.back);
        remove=findViewById(R.id.remove);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BannerHome.this,Banner.class);
                startActivity(intent);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BannerHome.this,BannerDelete.class);
                startActivity(intent);
            }
        });
    }
}
