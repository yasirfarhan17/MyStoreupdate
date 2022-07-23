package com.example.mystoreupdate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.BitmapCompat;

import id.zelory.compressor.Compressor;

public class product extends AppCompatActivity {
    EditText pName,pPrice,cWeight,HName;
    Button submit,back,img,productImg;
    DatabaseReference ref;
    String stock;
    String quantity[]={"Choose Weight","Per Kg","Per Dozen","Per Liter","Per Piece","Per Packet","Custom"};
    Spinner spinner,cat;
    ArrayAdapter<String> adapter;
    ImageView image;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    ArrayList<String> arr=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        //catName=(EditText)findViewById(R.id.catName);
        productImg=findViewById(R.id.product_img);
        pName=findViewById(R.id.productName);
        HName=findViewById(R.id.productNameHindi);
        pPrice=findViewById(R.id.productPrice);
        submit=findViewById(R.id.submitPriduct);
        spinner=findViewById(R.id.spinner);
        cat=(Spinner)findViewById(R.id.catSpinner);
        cWeight=findViewById(R.id.customWeight);
        back=findViewById(R.id.back);

        image=findViewById(R.id.banner_view);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        ref=FirebaseDatabase.getInstance().getReference("Category");
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, quantity);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);




        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = spinner.getSelectedItem().toString();

                if(str.equals("Custom")){
                    cWeight.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);
                    submit.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            if(pName.getText().toString().isEmpty()||productImg.getText().toString().isEmpty()||pPrice.getText().toString().isEmpty()||cat.getSelectedItem()==null||cat.getSelectedItemPosition()==0||cWeight.getText().toString().isEmpty()||HName.getText().toString().isEmpty()){
                                Toast.makeText(product.this,"Enter correct details",Toast.LENGTH_SHORT).show();
                            }
                            else{

                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    Bitmap bitmap=null ;

                                    if(mImageUri!=null) {
                                        try {
                                            bitmap = MediaStore.Images.Media.getBitmap(product.this.getContentResolver(), mImageUri);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    else {

                                        bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

                                    }


                                    long sizeBefor=bitmap.getAllocationByteCount();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                                    byte[] byteFormat = stream.toByteArray();
                                    long sizeImg=byteFormat.length;
                                Log.d("checkSize",""+sizeBefor+" "+sizeImg);
                                    String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
                                //compressedImageBitmap = Compressor.getDefault(product.this).compressToBitmap(actualImageFile);

                                String str=cat.getSelectedItem().toString();
                                String str1=spinner.getSelectedItem().toString();
                                productData ob =new productData(encodedImage,pPrice.getText().toString(),pName.getText().toString(),cWeight.getText().toString(),HName.getText().toString(),"yes");


                                ref=FirebaseDatabase.getInstance().getReference("CategoryProducts").child(str).child(pName.getText().toString());
                                ref.setValue(ob);
                                Toast.makeText(product.this,"Successfully Added",Toast.LENGTH_SHORT).show();

                                cat.setSelection(0);
                               image.setImageResource(R.drawable.ic_image_black_24dp);
                                pPrice.setText("");
                                pName.setText("");
                                cWeight.setText("");
                                HName.setText("");
                                spinner.setVisibility(View.VISIBLE);
                                spinner.setSelection(0);

                            }
                        }
                    });

                }
                else{
                    cWeight.setVisibility(View.GONE);
                    spinner.setVisibility(View.VISIBLE);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(pName.getText().toString().isEmpty()||productImg.getText().toString().isEmpty()||pPrice.getText().toString().isEmpty()||cat.getSelectedItem()==null||cat.getSelectedItemPosition()==0||spinner.getSelectedItemPosition()==0||HName.getText().toString().isEmpty()){
                                Toast.makeText(product.this,"Enter correct details",Toast.LENGTH_SHORT).show();
                            }
                            else{



                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                Bitmap bitmap=null ;

                                if(mImageUri!=null) {
                                    try {
                                        bitmap = MediaStore.Images.Media.getBitmap(product.this.getContentResolver(), mImageUri);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {

                                    bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

                                }


                                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                                byte[] byteFormat = stream.toByteArray();
                                String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

                                long sizeBefor=bitmap.getAllocationByteCount();
                                long sizeImg=byteFormat.length;

                                Log.d("checkSize",""+sizeBefor+" "+sizeImg);

                                String str=cat.getSelectedItem().toString();
                                String str1=spinner.getSelectedItem().toString();

                                productData ob=new productData(encodedImage,pPrice.getText().toString(),pName.getText().toString(),str1,HName.getText().toString(),"yes");

                                ref=FirebaseDatabase.getInstance().getReference("CategoryProducts").child(str).child(pName.getText().toString());
                                ref.setValue(ob);
                                Toast.makeText(product.this,"Successfully Added ",Toast.LENGTH_SHORT).show();

                                cat.setSelection(0);
                                image.setImageResource(R.drawable.ic_image_black_24dp);
                                pPrice.setText("");
                                pName.setText("");
                                HName.setText("");
                                spinner.setSelection(0);

                            }
                        }
                    });

                }
                // Toast.makeText(PaymentOption.this, clickedCountryName + " selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(image);
        }
    }
}
