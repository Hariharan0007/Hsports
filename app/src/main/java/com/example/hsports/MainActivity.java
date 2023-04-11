package com.example.hsports;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
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

        if(!isConnected(this)){
            System.out.println("Checking network connection !!!! ");
            showCustomDialog();
        }



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


    private boolean isConnected(MainActivity mainActivity) {
        ConnectivityManager con_manager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wificon = con_manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile_con = con_manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wificon!=null && wificon.isConnected()) || (mobile_con!=null && mobile_con.isConnected())){
            return true;
        }
        else{
            return false;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Please connect to internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                });
    }

}