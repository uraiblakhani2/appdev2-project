package com.example.healthpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationCompleted extends AppCompatActivity {

    Button send_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_completed);

        send_home = findViewById(R.id.send_home);

        send_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationCompleted.this, Home.class);
                startActivity(intent);

            }
        });


    }
}