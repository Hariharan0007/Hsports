package com.example.hsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class EnterData extends AppCompatActivity {
    private com.google.android.material.textfield.TextInputEditText d_name;
    private com.google.android.material.textfield.TextInputEditText d_organisation;
    private com.google.android.material.textfield.TextInputEditText d_event_name;
    private com.google.android.material.textfield.TextInputEditText d_event_year;
    private Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        d_name = findViewById(R.id.name);
        d_organisation = findViewById(R.id.organization);
        d_event_name = findViewById(R.id.event_name);
        d_event_year = findViewById(R.id.event_year);
        next_btn = findViewById(R.id.new_event_next);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = d_name.getText().toString().toUpperCase().trim();
                String org = d_organisation.getText().toString().toUpperCase().trim();
                String e_name = d_event_name.getText().toString().toUpperCase().trim();
                String e_year = d_event_year.getText().toString().trim();
                String event_name_year;
                if(validate_inputs(name,org,e_name,e_year)){
                    Intent create_event_team = new Intent(EnterData.this,CreateEventTeam.class);
                    event_name_year = e_name + ":" + e_year;
                    create_event_team.putExtra("creator_name",name);
                    create_event_team.putExtra("creator_org",org);
                    create_event_team.putExtra("event_name_year",event_name_year);
                    startActivity(create_event_team);
                    finish();
                }
            }
        });


    }

    private boolean validate_inputs(String name,String org,String e_name,String e_year){
        int flag = 0;
        if(name.isEmpty()){
            d_name.setError("Enter name");
            d_name.requestFocus();
            return false;
        }
        if(org.isEmpty()){
            d_organisation.setError("Enter your organisation");
            d_organisation.requestFocus();
            return false;
        }
        if(e_name.isEmpty()){
            d_event_name.setError("Enter event name");
            d_event_name.requestFocus();
            return false;
        }
        if(e_year.isEmpty()){
            d_event_year.setError("Select the date");
            d_event_year.requestFocus();
            return false;
        }
        
        return true;

    }

}