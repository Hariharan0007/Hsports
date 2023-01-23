package com.example.hsports;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RefereeActivity extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_activity);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Referees");

        String org = getIntent().getStringExtra("org");
        String organizer = getIntent().getStringExtra("organizer");
        String event = getIntent().getStringExtra("event");

        RecyclerView recyclerView = findViewById(R.id.activity_referee_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<RefereeViewModel> refereeList = new ArrayList<>();
        RefereeViewAdapter adapter = new RefereeViewAdapter(refereeList);
        recyclerView.setAdapter(adapter);

        Task<DataSnapshot> referee_list = database.child(org).child(organizer).child(event).child("EVENT TEAM").child("REFEREE").get();

        referee_list.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("TAG", "referee list: " + task.getResult().getValue());
                for (DataSnapshot referee : task.getResult().getChildren()) {
                    Log.d("TAG", "referee: " + referee.getKey() + " " + referee.getValue());
                    String name = Objects.requireNonNull(referee.getKey());
                    refereeList.add(new RefereeViewModel(name));
                }
                recyclerView.setAdapter(new RefereeViewAdapter(refereeList));
            }
        });

        FloatingActionButton addRefereeButton = findViewById(R.id.activity_referee_add_referee_button);

        addRefereeButton.setOnClickListener(v -> {
            Intent intent = new Intent(RefereeActivity.this, CreateEventTeam.class);
            intent.putExtra("creator_name", organizer);
            intent.putExtra("creator_org", org);
            intent.putExtra("event_name_year", event);
            startActivity(intent);
        });
    }
}
