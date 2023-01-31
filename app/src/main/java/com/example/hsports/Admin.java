package com.example.hsports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        refereeList.add(new ViewModel("Referee 1"));
        refereeList.add(new ViewModel("Referee 2"));
        refereeList.add(new ViewModel("Referee 3"));

        callRoomList.add(new ViewModel("Call Room 1"));
        callRoomList.add(new ViewModel("Call Room 2"));
        callRoomList.add(new ViewModel("Call Room 3"));

        dataEntryList.add(new ViewModel("Data Entry 1"));
        dataEntryList.add(new ViewModel("Data Entry 2"));
        dataEntryList.add(new ViewModel("Data Entry 3"));
    }
}