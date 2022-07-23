package com.example.mystoreupdate;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout=findViewById(R.id.grid);
        setSingleEvent(gridLayout);

        //startService(new Intent(this, myService.class));

    }
    private void setSingleEvent(GridLayout mGrid){
        for(int i=0;i<mGrid.getChildCount();i++){
            final CardView cardView=(CardView)mGrid.getChildAt(i);
            final int final1=i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cardView.getCardBackgroundColor().getDefaultColor()==-1){
                        cardView.setCardBackgroundColor(Color.parseColor("#41CEFC"));
                        //Toast.makeText(Visitor_Home.this,""+final1,Toast.LENGTH_LONG).show();

                    }
                    else{
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    }

                    if(final1==0){
                        Intent intent=new Intent(MainActivity.this,category.class);
                        //intent.putExtra("id",userId);
                        startActivity(intent);
                    }

                    if(final1==1){
                        Intent intent=new Intent(MainActivity.this,product1.class);
                        //intent.putExtra("id",userId);
                        startActivity(intent);
                    }
                    if(final1==2){
                        Intent intent=new Intent(MainActivity.this,OrderHomecheck.class);
                        //intent.putExtra("id",userId);
                        startActivity(intent);
                    }

                    if(final1==3){
                        Intent intent=new Intent(MainActivity.this,BannerHome.class);
                        //intent.putExtra("id",userId);
                        startActivity(intent);
                    }

                    if(final1==4){
                        Intent intent=new Intent(MainActivity.this,User.class);
                        //intent.putExtra("id",userId);
                        startActivity(intent);
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show_Notification(String title, String body){

        Intent intent=new Intent();
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText(body)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,title,pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


    }
}
