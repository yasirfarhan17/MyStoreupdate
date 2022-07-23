package com.example.mystoreupdate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class categoryAdd extends AppCompatActivity {
    EditText catName;
    DatabaseReference ref;
    Button submit,catImg;
    ImageView image;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
        catName=findViewById(R.id.catName);
        catImg=findViewById(R.id.cat_img);
        submit=findViewById(R.id.submitPriduct);
        image=findViewById(R.id.banner_view);

        catImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(catName.getText().toString().isEmpty()||mImageUri==null){
                    Toast.makeText(categoryAdd.this,"Enter Correct detail",Toast.LENGTH_SHORT).show();
                }
                else{

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap=null ;

                    if(mImageUri!=null) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(categoryAdd.this.getContentResolver(), mImageUri);
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
                    ref= FirebaseDatabase.getInstance().getReference("Category").child(catName.getText().toString());
                    categoryData obj=new categoryData(encodedImage,catName.getText().toString());
                    ref.setValue(obj);
                    //ref= FirebaseDatabase.getInstance().getReference("Product").child(catName.getText().toString());

                    Toast.makeText(categoryAdd.this,"SuccessFully Added!!!:)",Toast.LENGTH_SHORT).show();
                    catName.setText("");
                    image.setImageResource(R.drawable.ic_image_black_24dp);




                }


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
