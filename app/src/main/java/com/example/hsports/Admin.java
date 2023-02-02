package com.example.hsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

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

        RecyclerView callRoomRecyclerView = findViewById(R.id.activity_admin_call_room_recycler_view);
        RecyclerView refereeRecyclerView = findViewById(R.id.activity_admin_referee_recycler_view);
        RecyclerView dataEntryRecyclerView = findViewById(R.id.activity_admin_data_entry_recycler_view);

        callRoomRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        refereeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataEntryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<ViewModel> refereeList = new ArrayList<>();
        ArrayList<ViewModel> callRoomList = new ArrayList<>();
        ArrayList<ViewModel> dataEntryList = new ArrayList<>();

        ViewAdapter refereeAdapter = new ViewAdapter(refereeList);
        ViewAdapter callRoomAdapter = new ViewAdapter(callRoomList);
        ViewAdapter dataEntryAdapter = new ViewAdapter(dataEntryList);

        callRoomRecyclerView.setAdapter(callRoomAdapter);
        refereeRecyclerView.setAdapter(refereeAdapter);
        dataEntryRecyclerView.setAdapter(dataEntryAdapter);

        callRoomRecyclerView.setVisibility(View.GONE);
        refereeRecyclerView.setVisibility(View.GONE);
        dataEntryRecyclerView.setVisibility(View.GONE);

        callRoom.setOnClickListener(v -> switchVisibility(callRoomRecyclerView));
        referee.setOnClickListener(v -> switchVisibility(refereeRecyclerView));
        dataEntry.setOnClickListener(v -> switchVisibility(dataEntryRecyclerView));

        DatabaseReference refereeReference = database.child(org).child(organizer).child(event).child("EVENT TEAM").child("REFEREE");
        DatabaseReference callRoomReference = database.child(org).child(organizer).child(event).child("EVENT TEAM").child("CALL ROOM");
        DatabaseReference dataEntryReference = database.child(org).child(organizer).child(event).child("EVENT TEAM").child("DATA ENTRY");

        refereeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                refereeList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    refereeList.add(new ViewModel(dataSnapshot.getKey()));
                }
                refereeAdapter.setItems(refereeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Admin", "onCancelled: " + error.getMessage());
            }
        });

        callRoomReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callRoomList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    callRoomList.add(new ViewModel(dataSnapshot.getKey()));
                }
                callRoomAdapter.setItems(callRoomList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Admin", "onCancelled: " + error.getMessage());
            }
        });

        dataEntryReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataEntryList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataEntryList.add(new ViewModel(dataSnapshot.getKey()));
                }
                dataEntryAdapter.setItems(dataEntryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Admin", "onCancelled: " + error.getMessage());
            }
        });
    }

    private void switchVisibility(RecyclerView recyclerView) {
        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}