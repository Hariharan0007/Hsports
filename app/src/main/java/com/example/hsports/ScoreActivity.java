package com.example.hsports;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

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

        TextView round = findViewById(R.id.activity_score_round);
        TextView chestNo = findViewById(R.id.score_card_view_chest_no);
        TextView score = findViewById(R.id.score_card_view_score);
        Button saveAndNextButton = findViewById(R.id.activity_score_save_and_next);
        Button backButton = findViewById(R.id.activity_score_back);
        Button submitButton = findViewById(R.id.activity_score_submit);

        submitButton.setVisibility(Button.INVISIBLE);

        int totalRounds = 3;
        round.setText("Round 1");

        ArrayList<String> athleteList = new ArrayList<>();

        DatabaseReference gameReference = database.child(org).child(organizer).child(event).child("EVENT LIST").child(gender).child(category).child(game);

        Task<DataSnapshot> task = gameReference.get();

        task.addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                DataSnapshot snapshot = task1.getResult();
                for (DataSnapshot athlete : snapshot.getChildren()) {
                    athleteList.add(athlete.getKey());
                }
                chestNo.setText(athleteList.get(0));
            } else {
                Log.e("ScoreActivity", "Error getting data", task1.getException());
            }
        });

        final int[] roundNo = {1};
        final int[] athleteNo = {0};

        saveAndNextButton.setOnClickListener(v -> {
            if (roundNo[0] <= totalRounds) {
                String chestNoText = chestNo.getText().toString();
                String scoreText = score.getText().toString();

                HashMap<String, Object> scoreMap = new HashMap<>();
                scoreMap.put(String.valueOf(roundNo[0]), scoreText);

                gameReference.child(chestNoText).updateChildren(scoreMap);

                if (athleteNo[0] < athleteList.size() - 1) {
                    athleteNo[0]++;
                    chestNo.setText(athleteList.get(athleteNo[0]));
                } else {
                    if (roundNo[0] < totalRounds) {
                        roundNo[0]++;
                        round.setText("Round " + roundNo[0]);
                        athleteNo[0] = 0;
                        chestNo.setText(athleteList.get(athleteNo[0]));
                    } else {
                        saveAndNextButton.setVisibility(Button.INVISIBLE);
                        submitButton.setVisibility(Button.VISIBLE);
                    }
                }
            }

            score.setText("");
        });

        submitButton.setOnClickListener(v -> {
            Intent intent = new Intent(ScoreActivity.this, ResultActivity.class);
            intent.putExtra("org", org);
            intent.putExtra("organizer", organizer);
            intent.putExtra("event", event);
            intent.putExtra("gender", gender);
            intent.putExtra("category", category);
            intent.putExtra("game", game);
            startActivity(intent);
        });
    }
}
