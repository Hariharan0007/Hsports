package com.example.hsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEventTeam extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private com.google.android.material.textfield.TextInputEditText add_name;
    private com.google.android.material.textfield.TextInputEditText add_join_code;
    private AutoCompleteTextView position_dropdown;
    private ArrayAdapter<CharSequence> pos_adapter;
    private Button add_position,finish_creation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_team);

        add_name = findViewById(R.id.add_name);
        add_join_code = findViewById(R.id.add_join_code);
        add_position = findViewById(R.id.event_team_add);
        finish_creation = findViewById(R.id.event_team_finish);

        position_dropdown = findViewById(R.id.add_position_dropdown);
        pos_adapter = ArrayAdapter.createFromResource(CreateEventTeam.this,R.array.position,R.layout.dropdown);
        position_dropdown.setAdapter(pos_adapter);

        add_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = add_name.getText().toString().toUpperCase().trim();
                String join_code = add_join_code.getText().toString().trim();
                String position = position_dropdown.getText().toString().toUpperCase().trim();

                Intent create_event = getIntent();
                String creator_name = create_event.getStringExtra("creator_name");
                String creator_org = create_event.getStringExtra("creator_org");
                String event = create_event.getStringExtra("event_name_year");

                if(validate_inputs(name,join_code,position)){

                    database.child(creator_org).child(creator_name).child(event).child("EVENT TEAM").child(position).child(name).setValue(join_code);
                    Toast.makeText(CreateEventTeam.this,"Successfully added !!!",Toast.LENGTH_SHORT).show();
                    add_name.setText("");
                    add_join_code.setText("");
                }
            }
        });

        finish_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent finish_pos = new Intent(CreateEventTeam.this,MainActivity.class);
                startActivity(finish_pos);
                finish();
            }
        });

    }

    private boolean validate_inputs(String name,String code,String pos){
        if(name.isEmpty()){
            add_name.setError("Enter name");
            add_name.requestFocus();
            return false;
        }
        if(code.isEmpty()){
            add_join_code.setError("Enter code");
            add_join_code.requestFocus();
            return false;
        }
        if(code.length()<5){
            add_join_code.setError("Enter 5 digit code");
            add_join_code.requestFocus();
            return false;
        }
        return true;
    }
}