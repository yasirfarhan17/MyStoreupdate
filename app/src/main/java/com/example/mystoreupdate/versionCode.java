package com.example.mystoreupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class versionCode extends AppCompatActivity {
    EditText version;
    DatabaseReference ref;
    Button submit,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_code);
        submit=findViewById(R.id.submitcode);
        version=findViewById(R.id.version);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref=FirebaseDatabase.getInstance().getReference("version").child("version");
                ref.setValue(version.getText().toString());
                version.setText("");
                Toast.makeText(versionCode.this,"Succesfully added",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
