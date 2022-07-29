package com.example.mystoreupdate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class OrderActivity : AppCompatActivity() {


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        findViewById<CardView>(R.id.cv_new_order).setOnClickListener {
            val intent = Intent(this, OrderNewActivity::class.java)
            startActivity(intent)

        }
        findViewById<CardView>(R.id.cv_old_order).setOnClickListener {
            val intent = Intent(this, OrderHomecheck::class.java)
            startActivity(intent)
        }


    }
}