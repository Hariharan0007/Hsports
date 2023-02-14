package com.example.hsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Referee extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee);

        String org = getIntent().getStringExtra("org");
        String organizer = getIntent().getStringExtra("organizer");
        String event = getIntent().getStringExtra("event");

        Spinner spinner = findViewById(R.id.activity_referee_gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView jump = findViewById(R.id.activity_referee_jump);
        TextView throw_ = findViewById(R.id.activity_referee_throw);
        TextView track = findViewById(R.id.activity_referee_track);

        RecyclerView jumpRecyclerView = findViewById(R.id.activity_referee_jump_recycler_view);
        RecyclerView throwRecyclerView = findViewById(R.id.activity_referee_throw_recycler_view);
        RecyclerView trackRecyclerView = findViewById(R.id.activity_referee_track_recycler_view);

        jumpRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        throwRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        trackRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<ViewModel> jumpList = new ArrayList<>();
        ArrayList<ViewModel> throwList = new ArrayList<>();
        ArrayList<ViewModel> trackList = new ArrayList<>();

        ViewAdapter jumpAdapter = new ViewAdapter(jumpList);
        ViewAdapter throwAdapter = new ViewAdapter(throwList);
        ViewAdapter trackAdapter = new ViewAdapter(trackList);

        jumpRecyclerView.setAdapter(jumpAdapter);
        throwRecyclerView.setAdapter(throwAdapter);
        trackRecyclerView.setAdapter(trackAdapter);

        jumpRecyclerView.setVisibility(View.GONE);
        throwRecyclerView.setVisibility(View.GONE);
        trackRecyclerView.setVisibility(View.GONE);

        jump.setOnClickListener(v -> switchVisibility(jumpRecyclerView));
        throw_.setOnClickListener(v -> switchVisibility(throwRecyclerView));
        track.setOnClickListener(v -> switchVisibility(trackRecyclerView));

        final DatabaseReference[] jumpReference = {database.child(org).child(organizer).child(event).child("EVENT LIST").child(spinner.getSelectedItem().toString().toUpperCase()).child("JUMP")};
        final DatabaseReference[] throwReference = {database.child(org).child(organizer).child(event).child("EVENT LIST").child(spinner.getSelectedItem().toString().toUpperCase()).child("THROW")};
        final DatabaseReference[] trackReference = {database.child(org).child(organizer).child(event).child("EVENT LIST").child(spinner.getSelectedItem().toString().toUpperCase()).child("TRACK")};

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jumpReference[0] = database.child(org).child(organizer).child(event).child("EVENT LIST").child(spinner.getSelectedItem().toString().toUpperCase()).child("JUMP");
                throwReference[0] = database.child(org).child(organizer).child(event).child("EVENT LIST").child(spinner.getSelectedItem().toString().toUpperCase()).child("THROW");
                trackReference[0] = database.child(org).child(organizer).child(event).child("EVENT LIST").child(spinner.getSelectedItem().toString().toUpperCase()).child("TRACK");

                jumpReference[0].addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        jumpList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            jumpList.add(new ViewModel(dataSnapshot.getKey()));
                        }
                        jumpAdapter.setItems(jumpList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Referee", "onCancelled: ", error.toException());
                    }
                });

                throwReference[0].addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        throwList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            throwList.add(new ViewModel(dataSnapshot.getKey()));
                        }
                        throwAdapter.setItems(throwList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Referee", "onCancelled: ", error.toException());
                    }
                });

                trackReference[0].addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        trackList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            trackList.add(new ViewModel(dataSnapshot.getKey()));
                        }
                        trackAdapter.setItems(trackList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Referee", "onCancelled: ", error.toException());
                    }
                });

                jumpRecyclerView.setVisibility(View.GONE);
                throwRecyclerView.setVisibility(View.GONE);
                trackRecyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        jumpReference[0].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jumpList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    jumpList.add(new ViewModel(dataSnapshot.getKey()));
                }
                jumpAdapter.setItems(jumpList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Referee", "onCancelled: ", error.toException());
            }
        });

        throwReference[0].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                throwList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    throwList.add(new ViewModel(dataSnapshot.getKey()));
                }
                throwAdapter.setItems(throwList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Referee", "onCancelled: ", error.toException());
            }
        });

        trackReference[0].addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trackList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    trackList.add(new ViewModel(dataSnapshot.getKey()));
                }
                trackAdapter.setItems(trackList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Referee", "onCancelled: ", error.toException());
            }
        });

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                String game = intent.getStringExtra("name");
                Intent intent_ = new Intent(Referee.this, ScoreActivity.class);
                intent_.putExtra("org", org);
                intent_.putExtra("organizer", organizer);
                intent_.putExtra("event", event);
                intent_.putExtra("gender", spinner.getSelectedItem().toString().toUpperCase());
                intent_.putExtra("category", getCategory(game));
                intent_.putExtra("game", game);
                startActivity(intent_);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("broadcast"));
    }

    private void switchVisibility(RecyclerView recyclerView) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private String getCategory(String game) {
        String[] jumps = {"LONG JUMP", "TRIPLE JUMP", "HIGH JUMP", "POLE VAULT"};
        String[] throws_ = {"DISCUS THROW", "HAMMER THROW", "JAVELIN THROW", "SHOT PUT"};

        for (String jump : jumps) {
            if (jump.equalsIgnoreCase(game)) {
                return "JUMP";
            }
        }
        for (String throw_ : throws_) {
            if (throw_.equalsIgnoreCase(game)) {
                return "THROW";
            }
        }

        return "TRACK";
    }
}