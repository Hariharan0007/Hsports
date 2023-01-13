package com.example.hsports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kotlin.jvm.internal.Ref;

public class JoinEventTeam extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private com.google.android.material.textfield.TextInputEditText j_name;
    private com.google.android.material.textfield.TextInputEditText j_join_code;
    private com.google.android.material.textfield.TextInputLayout j_org_layout,j_organizer_layout;
    private AutoCompleteTextView join_position_dropdown,join_event_organisation,join_event_organizer,join_event_list;
    private ArrayAdapter<CharSequence> pos_adapter;
    private ArrayAdapter org_adapter,organizer_adapter,event_adapter;
    private String selected_org,selected_organizer,selected_event;
    private Button join_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event_team);

        j_name = findViewById(R.id.join_p_name);
        j_join_code = findViewById(R.id.join_join_code);
        join_event = findViewById(R.id.join_event_team_btn);
        j_org_layout = findViewById(R.id.join_event_organisation_layout);
        j_organizer_layout = findViewById(R.id.join_event_organizer_layout);

        join_position_dropdown = findViewById(R.id.join_position_dropdown);
        pos_adapter = ArrayAdapter.createFromResource(JoinEventTeam.this,R.array.position,R.layout.dropdown);
        join_position_dropdown.setAdapter(pos_adapter);


        ArrayList<String> organisation = new ArrayList<String>();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot value : snapshot.getChildren()){
                    String data = value.getKey();
                    organisation.add(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        join_event_organisation = findViewById(R.id.join_event_organisation);
        ArrayAdapter<String> org_adapter = new ArrayAdapter<>(this,R.layout.dropdown,organisation);
        org_adapter.setDropDownViewResource(R.layout.dropdown);
        join_event_organisation.setAdapter(org_adapter);


        ArrayList<String> organizer = new ArrayList<String>();
        join_event_organizer = findViewById(R.id.join_event_organizer);

        ((AutoCompleteTextView)j_org_layout.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selected_org = org_adapter.getItem(position);
                database.child(selected_org).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        organizer.clear();
                        for(DataSnapshot value : snapshot.getChildren()){
                            String data = value.getKey();
                            organizer.add(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        ArrayAdapter<String> organizer_adapter = new ArrayAdapter<>(this,R.layout.dropdown,organizer);
        organizer_adapter.setDropDownViewResource(R.layout.dropdown);
        join_event_organizer.setAdapter(organizer_adapter);

        join_event_list = findViewById(R.id.join_event_list);
        ArrayList<String> event_list = new ArrayList<String>();

        ((AutoCompleteTextView)j_organizer_layout.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selected_organizer = organizer_adapter.getItem(position);
                database.child(selected_org).child(selected_organizer).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        event_list.clear();
                        for (DataSnapshot value : snapshot.getChildren()){
                            String data = value.getKey();
                            event_list.add(data);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        ArrayAdapter<String> event_list_adapter = new ArrayAdapter<>(this,R.layout.dropdown,event_list);
        event_list_adapter.setDropDownViewResource(R.layout.dropdown);
        join_event_list.setAdapter(event_list_adapter);



        join_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = j_name.getText().toString().toUpperCase().trim();
                String code = j_join_code.getText().toString().trim();
                selected_org = join_event_organisation.getText().toString().toUpperCase().trim();
                selected_organizer = join_event_organizer.getText().toString().toUpperCase().trim();
                selected_event = join_event_list.getText().toString().toUpperCase().trim();
                String position = join_position_dropdown.getText().toString().toUpperCase().trim();

                if (validate_inputs(name,code,position,selected_org,selected_organizer,selected_event)){
                    System.out.println("Selected event == > " + selected_event);
                    System.out.println("Organisation == > " + organisation);
                    database.child(selected_org).child(selected_organizer).child(selected_event).child("EVENT TEAM").child(position).child(name).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String check_code = snapshot.getValue().toString();
                                if(check_code.equals(code)){

                                    Toast.makeText(JoinEventTeam.this,"Valid !!!",Toast.LENGTH_SHORT).show();
                                    if(position.equals("ADMIN")){
                                        Intent admin_activity = new Intent(JoinEventTeam.this,Admin.class);
                                        admin_activity.putExtra("org",selected_org);
                                        admin_activity.putExtra("organizer",selected_organizer);
                                        admin_activity.putExtra("event",selected_event);
                                        startActivity(admin_activity);
                                        finish();
                                    }
                                    else if(position.equals("DATA ENTRY")){
                                        Intent data_entry_activity = new Intent(JoinEventTeam.this,DataEntry.class);
                                        data_entry_activity.putExtra("org",selected_org);
                                        data_entry_activity.putExtra("organizer",selected_organizer);
                                        data_entry_activity.putExtra("event",selected_event);
                                        startActivity(data_entry_activity);
                                        finish();
                                    }
                                    else if(position.equals("CALL ROOM")){
                                        Intent call_room_activity = new Intent(JoinEventTeam.this,CallRoom.class);
                                        call_room_activity.putExtra("org",selected_org);
                                        call_room_activity.putExtra("organizer",selected_organizer);
                                        call_room_activity.putExtra("event",selected_event);
                                        startActivity(call_room_activity);
                                        finish();
                                    }
                                    else if(position.equals("REFEREE")){
                                        Intent referee_activity = new Intent(JoinEventTeam.this, Referee.class);
                                        referee_activity.putExtra("org",selected_org);
                                        referee_activity.putExtra("organizer",selected_organizer);
                                        referee_activity.putExtra("event",selected_event);
                                        startActivity(referee_activity);
                                        finish();
                                    }
                                }
                                else{
                                    Toast.makeText(JoinEventTeam.this,"Invalid code!!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(JoinEventTeam.this,"Invalid data!!!",Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }

    private boolean validate_inputs(String name,String code,String pos,String selected_org,String selected_organizer,String selected_event){
        if(selected_org.isEmpty()){
            join_event_organisation.setError("Choose organisation");
            join_event_organisation.requestFocus();
            return false;
        }

        if(selected_organizer.isEmpty()){
            join_event_organizer.setError("Choose organizer");
            join_event_organizer.requestFocus();
            return false;
        }

        if(selected_event.isEmpty()){
            join_event_list.setError("Choose event");
            join_event_list.requestFocus();
            return false;
        }

        if(pos.isEmpty()){
            join_position_dropdown.setError("Choose position");
            join_position_dropdown.requestFocus();
            return false;
        }

        if(name.isEmpty()){
            j_name.setError("Enter name");
            j_name.requestFocus();
            return false;
        }

        if(code.isEmpty()){
            j_join_code.setError("Enter code");
            j_join_code.requestFocus();
            return false;
        }

        if(code.length()<5){
            j_join_code.setError("Enter 5 digit code");
            j_join_code.requestFocus();
            return false;
        }


        return true;
    }
}











//((AutoCompleteTextView)textInputLayout.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
//@Override
//public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        String selectedValue = arrayAdapter.getItem(position);
//        }
//        });