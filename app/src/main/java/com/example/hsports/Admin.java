package com.example.hsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        String org = getIntent().getStringExtra("org");
        String organizer = getIntent().getStringExtra("organizer");
        String event = getIntent().getStringExtra("event");

        CardView callRoom = findViewById(R.id.activity_admin_call_room);
        CardView referee = findViewById(R.id.activity_admin_referee);
        CardView dataEntry = findViewById(R.id.activity_admin_data_entry);

        callRoom.setOnClickListener(v -> {
            Intent intent = new Intent(Admin.this, CallRoomActivity.class);
            intent.putExtra("org", org);
            intent.putExtra("organizer", organizer);
            intent.putExtra("event", event);
            startActivity(intent);
        });

        referee.setOnClickListener(v -> {
            Intent intent = new Intent(Admin.this, RefereeActivity.class);
            intent.putExtra("org", org);
            intent.putExtra("organizer", organizer);
            intent.putExtra("event", event);
            startActivity(intent);
        });

        dataEntry.setOnClickListener(v -> {
            Intent intent = new Intent(Admin.this, DataEntryActivity.class);
            intent.putExtra("org", org);
            intent.putExtra("organizer", organizer);
            intent.putExtra("event", event);
            startActivity(intent);
        });
    }
}