package com.example.mystoreupdate;

import android.content.DialogInterface;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class categoryRemove extends AppCompatActivity {
    EditText remove;
    Button button,back;
    DatabaseReference ref;
    Spinner cat;
    ArrayList<String> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_remove);
        cat = (Spinner) findViewById(R.id.catSpinner);
        button = findViewById(R.id.removebtn);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        ref = FirebaseDatabase.getInstance().getReference("Category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arr.clear();
                arr.add("Select Category");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String val = dataSnapshot1.getKey();
                    arr.add(val);

                }
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arr);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                cat.setAdapter(adapter);
                cat.setSelection(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cat.getSelectedItemPosition() == 0) {
                    Toast.makeText(categoryRemove.this, "Enter Category Name", Toast.LENGTH_SHORT).show();

                } else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            categoryRemove.this);

                    // set title
                    alertDialogBuilder.setTitle("Exit");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Do you really want to delete?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    String str=cat.getSelectedItem().toString();
                                    ref=FirebaseDatabase.getInstance().getReference("Category");
                                    ref.child(str).removeValue();
                                    ref=FirebaseDatabase.getInstance().getReference("CategoryProduct");
                                    ref.child(str).removeValue();
                                    Toast.makeText(categoryRemove.this, "Remove Successful", Toast.LENGTH_SHORT).show();
                                    cat.setSelection(0);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }
            }
        });
    }
}
