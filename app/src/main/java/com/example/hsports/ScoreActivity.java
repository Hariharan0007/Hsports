package com.example.hsports;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        String org = getIntent().getStringExtra("org");
        String organizer = getIntent().getStringExtra("organizer");
        String event = getIntent().getStringExtra("event");
        String gender = getIntent().getStringExtra("gender");
        String category = getIntent().getStringExtra("category");
        String game = getIntent().getStringExtra("game");

        ArrayList<ViewModel> athleteList = new ArrayList<>();

        DatabaseReference gameReference = database.child(org).child(organizer).child(event).child("EVENT LIST").child(gender).child(category).child(game);

        gameReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.println(Log.INFO, "ScoreActivity: ", dataSnapshot.getKey() + " " + dataSnapshot.getValue());
                    athleteList.add(new ViewModel(dataSnapshot.getKey()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ScoreActivity: ", "onCancelled: " + error.getMessage());
            }
        });
    }
}
