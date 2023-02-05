package com.example.hsports;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

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

        DatabaseReference scoreReference = database.child(org).child(organizer).child(event).child("SCORES").child(gender).child(category).child(game);
        DatabaseReference resultReference = database.child(org).child(organizer).child(event).child("RESULTS").child(gender).child(category).child(game);

        ArrayList<Score> scoreList = new ArrayList<>();

        scoreReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DataSnapshot athlete : task.getResult().getChildren()) {
                    ArrayList<String> scores = new ArrayList<>();
                    for (DataSnapshot score : athlete.getChildren()) {
                        scores.add(Objects.requireNonNull(score.getValue()).toString());
                    }
                    scoreList.add(new Score(athlete.getKey(), scores));
                    Log.println(Log.DEBUG, "ResultActivity:", scores.toString());
                }
                sortScoreList(scoreList, resultReference);
                displayResults(scoreList);
            } else {
                Log.e("ResultActivity", "Error getting data", task.getException());
            }
        });
    }

    private ArrayList<Result> sortScoreList(ArrayList<Score> scoreList, DatabaseReference resultReference) {
        // remove the dob and name from the score list and sort the scores
        for (Score score : scoreList) {
            score.cleanseScores();
            score.sortScores();
        }

        Collections.sort(scoreList, (x, y) -> {
            int i = 0;
            while (i < x.getScores().size() && i < y.getScores().size()) {
                if (x.get(i) > y.get(i)) {
                    return -1;
                } else if (x.get(i) < y.get(i)) {
                    return 1;
                }
                i++;
            }
            return 0;
        });

        ArrayList<Result> results = new ArrayList<>();
        for (int i = 0; i < scoreList.size(); i++) {
            results.add(new Result(scoreList.get(i).getChestNo(), scoreList.get(i).get(0).toString(), String.valueOf(i + 1)));
        }

        for (Result result : results) {
            Log.println(Log.DEBUG, "ResultActivity:", result.toString());
        }

        // save the results to the database
        HashMap<String, HashMap<String, String>> resultMap = new HashMap<>();
        for (Result result : results) {
            resultMap.put(result.getPosition(), result.toMap());
        }
        resultReference.setValue(resultMap);

        return results;
    }

    private void displayResults(ArrayList<Score> scoreList) {
        TableLayout tableLayout = findViewById(R.id.activity_result_table);

        for (int i = 0; i < scoreList.size() && i < 3; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setPadding(0, 0, 0, 10);

            TextView position = new TextView(this);
            position.setText(String.valueOf(i + 1));
            position.setPadding(0, 0, 10, 0);
            tableRow.addView(position);

            TextView chestNo = new TextView(this);
            chestNo.setText(scoreList.get(i).getChestNo());
            chestNo.setPadding(0, 0, 10, 0);
            tableRow.addView(chestNo);

            TextView score = new TextView(this);
            score.setText(scoreList.get(i).get(0).toString());
            tableRow.addView(score);
            tableRow.setPadding(0, 0, 0, 10);

            tableLayout.addView(tableRow);
        }
    }
}
