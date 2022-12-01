package com.example.healthpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {
    TextView user_name2;
    DatabaseReference reff;
    Button add;

    ImageButton user_profile;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthpal-1905e-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user_name2 = findViewById(R.id.user_name2);
        user_profile = findViewById(R.id.user_profile);
        add = findViewById(R.id.add);
        reff = FirebaseDatabase.getInstance().getReference().child("Member");

        String userids = getIntent().getStringExtra("user");
        user_name2.setText("Welcome " + userids);


        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ione = new Intent(getApplicationContext(), EditProfile.class);
                ione.putExtra("user",userids);
                startActivity(ione);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ione = new Intent(getApplicationContext(), AddMedicine.class);
                ione.putExtra("user",userids);
                startActivity(ione);

            }
        });

//        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                final String getPassword = snapshot.child(user_name).child("password").getValue(String.class);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
}