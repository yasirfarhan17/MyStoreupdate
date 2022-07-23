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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class userAdapter extends RecyclerView.Adapter <userAdapter.MyViewHolder>{

    ArrayList<userData> product1=new ArrayList<>();
    //int count=1;
    private OnItemClickListener mListner;
    DatabaseReference ref;
    ArrayList<String> val=new ArrayList<>();
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    String currentTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());
    SharedPreferences preferences ;
    String key;
    Context context;



    public userAdapter(ArrayList<userData> p){
        product1=p;
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
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.userview,parent,false);
        MyViewHolder evh=new MyViewHolder(v,mListner);

        return  evh;
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.name.setText("Name :-"+product1.get(position).getName());
        holder.phone.setText("Phone No. :-"+product1.get(position).getPhone());

        if(product1.get(position).getOnline().equals("on")){
            holder.img.setVisibility(View.VISIBLE);
        }
        else {
            holder.img.setVisibility(View.INVISIBLE);
        }




    }

    @Override
    public int getItemCount() {
        return product1.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,phone;
        ImageView img;

        //final AdapterView.OnItemClickListener listener;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            phone=itemView.findViewById(R.id.phone);
            img=itemView.findViewById(R.id.light);








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
