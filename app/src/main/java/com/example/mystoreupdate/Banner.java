package com.example.mystoreupdate;

import androidx.appcompat.app.AppCompatActivity;

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

public class Banner extends AppCompatActivity {
    EditText color;
    Button submit,def,url;
    ImageView image;
    DatabaseReference ref;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        url=findViewById(R.id.banner_img);
        color=findViewById(R.id.banner_color);
        submit=findViewById(R.id.submit);
        def=findViewById(R.id.def);
        back=findViewById(R.id.back);
        image=findViewById(R.id.banner_view);

        def.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color.setText("#FFFFFF");
            }
        });
        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mImageUri==null||color.getText().toString().isEmpty()){
                    Toast.makeText(Banner.this,"Please Enter Detail",Toast.LENGTH_SHORT).show();

                }
                else {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap=null ;

                    if(mImageUri!=null) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(Banner.this.getContentResolver(), mImageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {

                        bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

                    }


                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteFormat = stream.toByteArray();
                    String encodedImage = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

                    BannerData ob = new BannerData(encodedImage, color.getText().toString());
                    ref = FirebaseDatabase.getInstance().getReference("Banner");
                    ref.push().setValue(ob);
                    Toast.makeText(Banner.this,"Please Enter Detail",Toast.LENGTH_SHORT).show();
                    image.setImageResource(R.drawable.ic_apps_black_24dp);
                    color.setText("");
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
