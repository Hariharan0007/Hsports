package com.example.hsports;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String org = getIntent().getStringExtra("org");
        String organizer = getIntent().getStringExtra("organizer");
        String event = getIntent().getStringExtra("event");
        String gender = getIntent().getStringExtra("gender");
        String category = getIntent().getStringExtra("category");
        String game = getIntent().getStringExtra("game");

        DatabaseReference gameReference = database.child(org).child(organizer).child(event).child("EVENT LIST").child(gender).child(category).child(game);
        DatabaseReference resultReference = database.child(org).child(organizer).child(event).child("RESULTS");

        ArrayList<HashMap<String, HashMap<String, String>>> scoreList = new ArrayList<>();

        gameReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot athlete : task.getResult().getChildren()) {
                    HashMap<String, String> score = new HashMap<>();
                }
            } else {
                Log.e("ScoreActivity", "Error getting data", task.getException());
            }
        });
    }
}
