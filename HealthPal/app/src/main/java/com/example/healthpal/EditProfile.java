package com.example.healthpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    EditText editName, editEmail, editUsername, editPassword;
    Button update;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthpal-1905e-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        update = findViewById(R.id.update);
        editPassword = findViewById(R.id.editPassword);

        String userids = getIntent().getStringExtra("user");


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getPassword = snapshot.child(userids).child("password").getValue(String.class);
                final String getName = snapshot.child(userids).child("fullname").getValue(String.class);
                final String getEmail = snapshot.child(userids).child("email").getValue(String.class);
                editPassword.setText(getPassword);
                editEmail.setText(getEmail);
                editName.setText(getName);
                editUsername.setText(userids);

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("users").child(userids).child("fullname").setValue(editName.getText().toString());
                        editName.setText(editName.getText().toString());
                        //databaseReference.child("users").child(userids).setValue(editUsername.getText().toString());
                        databaseReference.child("users").child(userids).child("email").setValue(editEmail.getText().toString());
                        databaseReference.child("users").child(userids).child("password").setValue(editPassword.getText().toString());
                        Toast.makeText(EditProfile.this, "User Profile updated successfully", Toast.LENGTH_SHORT).show();

                        final String getPassword = snapshot.child(userids).child("password").getValue(String.class);
                        final String getName = snapshot.child(userids).child("fullname").getValue(String.class);
                        final String getEmail = snapshot.child(userids).child("email").getValue(String.class);
                        editPassword.setText(editPassword.getText().toString());
                        editEmail.setText(editEmail.getText().toString());
                        editName.setText(editName.getText().toString());
                        editUsername.setText(editUsername.getText().toString());


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }
}