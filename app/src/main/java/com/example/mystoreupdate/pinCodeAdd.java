package com.example.mystoreupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class pinCodeAdd extends AppCompatActivity {
    EditText add;
    Button back,submit;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code_add);
        add=findViewById(R.id.Epin);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submit=findViewById(R.id.EButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinData ob=new pinData(add.getText().toString(),"on");
                ref= FirebaseDatabase.getInstance().getReference("PinCode");
                ref.child(add.getText().toString()).setValue(ob);
                add.setText("");
                Toast.makeText(pinCodeAdd.this,"successFully added",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
