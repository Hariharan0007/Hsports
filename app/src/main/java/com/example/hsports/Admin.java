package com.example.hsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        CardView callRoom = findViewById(R.id.activity_admin_call_room);
        CardView referee = findViewById(R.id.activity_admin_referee);
        CardView dataEntry = findViewById(R.id.activity_admin_data_entry);


    }
}