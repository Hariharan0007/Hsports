package com.example.hsports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DataEntry extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private com.google.android.material.textfield.TextInputEditText chest_number;
    private com.google.android.material.textfield.TextInputEditText player_name;
    private com.google.android.material.textfield.TextInputEditText dob;
    private com.google.android.material.textfield.TextInputLayout event_type_layout;
//    private String selected_event_type;
    private Button d_o_b,add_btn,finish_btn;

    private AutoCompleteTextView event_type,event_name,gender;

    private ArrayAdapter<CharSequence> gender_adapter;
    private ArrayAdapter<CharSequence> event_type_adapter,event_name_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        player_name = findViewById(R.id.data_entry_player_name);
        chest_number = findViewById(R.id.data_entry_chest_number);
        dob = findViewById(R.id.data_entry_dob);
        event_type = findViewById(R.id.data_entry_event_type);
        event_name = findViewById(R.id.data_entry_event_name);
        gender = findViewById(R.id.data_entry_gender);
        event_type_layout = findViewById(R.id.data_entry_event_type_layout);
        d_o_b = findViewById(R.id.dob_select_btn);
        add_btn = findViewById(R.id.data_entry_add_player);
        finish_btn = findViewById(R.id.data_entry_finish);

        event_type_adapter = ArrayAdapter.createFromResource(DataEntry.this,R.array.event_type,R.layout.dropdown);
        event_type.setAdapter(event_type_adapter);

        gender_adapter = ArrayAdapter.createFromResource(DataEntry.this,R.array.gender,R.layout.dropdown);
        gender.setAdapter(gender_adapter);

        d_o_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialDatePicker<Long> materialdatepicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select dob").build();
                materialdatepicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        // Get the offset from our timezone and UTC.
                        TimeZone timeZoneUTC = TimeZone.getDefault();
                        // It will be negative, so that's the -1
                        int offsetFromUTC = timeZoneUTC.getOffset(new Date().getTime()) * -1;
                        // Create a date format, then a date object with our offset
                        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);  //yyyy-MM-dd
                        Date date = new Date(selection + offsetFromUTC);

                        dob.setText(simpleFormat.format(date));
                    }
                });

//                MaterialDatePicker materialdatepicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select dob").build();
//
//
//
//                materialdatepicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
//                    @Override
//                    public void onPositiveButtonClick(Object selection) {
//                        String date_format = materialdatepicker.getHeaderText();
//
//                        dob.setText("" + materialdatepicker.getHeaderText());
//                    }
//                });

                materialdatepicker.show(getSupportFragmentManager(),"TAG");
            }
        });


        ((AutoCompleteTextView)event_type_layout.getEditText()).setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String selected_event_type= event_type_adapter.getItem(position).toString();
            switch (selected_event_type){
                case "Track":
                    event_name_adapter = ArrayAdapter.createFromResource(DataEntry.this,R.array.event_track,R.layout.dropdown);
                    event_name.setAdapter(event_name_adapter);
                    break;
                case "Jump":
                    event_name_adapter = ArrayAdapter.createFromResource(DataEntry.this,R.array.event_jump,R.layout.dropdown);
                    event_name.setAdapter(event_name_adapter);
                    break;
                case "Throw":
                    event_name_adapter = ArrayAdapter.createFromResource(DataEntry.this,R.array.event_throw,R.layout.dropdown);
                    event_name.setAdapter(event_name_adapter);
                    break;
            }
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Getting data from the XML file
                String s_event_type = event_type.getText().toString().toUpperCase();
                String s_event_name = event_name.getText().toString().toUpperCase();
                String s_chest_no = chest_number.getText().toString().trim();
                String s_player_name = player_name.getText().toString().toUpperCase().trim();
                String s_gender = gender.getText().toString().toUpperCase();
                String s_dob = dob.getText().toString().trim();
                System.out.println(s_dob);

//                Getting data of organisation ,organizer and event
                Intent data_entry = getIntent();
                String org = data_entry.getStringExtra("org");
                String organizer = data_entry.getStringExtra("organizer");
                String event = data_entry.getStringExtra("event");

                if(validate_inputs(s_event_type,s_event_name,s_chest_no,s_player_name,s_gender,s_dob)){
                    database.child(org).child(organizer).child(event).child("EVENT LIST").child(s_event_type).child(s_event_name).child(s_gender).child(s_chest_no).child("NAME").setValue(s_player_name);
                    database.child(org).child(organizer).child(event).child("EVENT LIST").child(s_event_type).child(s_event_name).child(s_gender).child(s_chest_no).child("DOB").setValue(s_dob);
                    event_name.setText("");
                    chest_number.setText("");
                    player_name.setText("");
                    gender.setText("");
                    dob.setText("");
                    Toast.makeText(DataEntry.this,"Player added",Toast.LENGTH_SHORT).show();
                }
            }
        });

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private boolean validate_inputs(String e_type,String e_name,String c_no,String p_name,String gen,String s_dob){
        if(e_type.isEmpty()){
            event_type.setError("Select Event type");
            event_type.requestFocus();
            return false;
        }

        if(e_name.isEmpty()){
            event_name.setError("Select Event name");
            event_name.requestFocus();
            return false;
        }

        if(c_no.isEmpty()){
            chest_number.setError("Enter chest number");
            chest_number.requestFocus();
            return false;
        }

        if(p_name.isEmpty()){
            player_name.setError("Enter player name");
            player_name.requestFocus();
            return false;
        }

        if(gen.isEmpty()){
            gender.setError("Select gender");
            gender.requestFocus();
            return false;
        }

        if(s_dob.isEmpty()){
            dob.setError("Choose DOB");
            dob.requestFocus();
            return false;
        }
        return true;
    }
}