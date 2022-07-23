package com.example.mystoreupdate;



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BannerAdapter extends RecyclerView.Adapter <BannerAdapter.MyViewHolder>{

    ArrayList<BannerData> product1=new ArrayList<>();
    //int count=1;
    private OnItemClickListener mListner;
    DatabaseReference ref;
    ArrayList<String> val=new ArrayList<>();
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    String currentTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());
    Context context;
    String a;


    public BannerAdapter(ArrayList<BannerData> p,Context c){
        product1=p;
        context=c;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void setText(TextView box, int val);

    }



    public void setOnItemClickListener(OnItemClickListener listener){
        mListner=listener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.bannerview,parent,false);
        MyViewHolder evh=new MyViewHolder(v,mListner);

        return  evh;
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        byte[] decodedString = Base64.decode(product1.get(position).getImg(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Glide
                .with(context.getApplicationContext())
                .load(decodedByte)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(200,200)
                        .placeholder(R.drawable.ic_apps_black_24dp))
                .into(holder.img);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ref=FirebaseDatabase.getInstance().getReference("Banner");
               // Toast.makeText(view.getContext(),""+product1.get(position).getImg(),Toast.LENGTH_SHORT).show();
                ref.orderByChild("img").equalTo(product1.get(position).getImg()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            a=dataSnapshot1.getKey();
                            //Toast.makeText(view.getContext(),""+a,Toast.LENGTH_SHORT).show();
                            ref=FirebaseDatabase.getInstance().getReference("Banner").child(a);
                            ref.removeValue();
                            product1.remove(position);

                        }

                        //

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return product1.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img,img1;
        Button btnIns,btnDsc,addBtn;

        //final AdapterView.OnItemClickListener listener;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.product_img);
            addBtn=(Button)itemView.findViewById(R.id.addBtn);








            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });




        }



    }
}