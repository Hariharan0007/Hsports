package com.example.hsports;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button create_btn,join_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_btn = findViewById(R.id.create);
        join_btn = findViewById(R.id.join);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create_event_next = new Intent(MainActivity.this,EnterData.class);
                startActivity(create_event_next);
            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent join_event = new Intent(MainActivity.this,JoinEventTeam.class);
                startActivity(join_event);
            }
        });
    }

}