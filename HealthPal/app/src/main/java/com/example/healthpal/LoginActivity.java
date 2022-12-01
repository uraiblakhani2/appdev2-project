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

public class LoginActivity extends AppCompatActivity {
    Button login, register, forgotPassword;
    EditText textUsername, textPassword;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthpal-1905e-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login3);
        register = findViewById(R.id.register4);
        textUsername = findViewById(R.id.textUsername);
        textPassword = findViewById(R.id.textPassword);
        forgotPassword = findViewById(R.id.forgotPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_name = textUsername.getText().toString();
                String password = textPassword.getText().toString();

                if(user_name.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();

                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(user_name)){
                                final String getPassword = snapshot.child(user_name).child("password").getValue(String.class);
                                if(getPassword.equals(password)){
                                    Toast.makeText(LoginActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();

                                    Intent ione = new Intent(getApplicationContext(), Home.class);
                                    ione.putExtra("user",user_name);
                                    startActivity(ione);
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();

                                }
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
}