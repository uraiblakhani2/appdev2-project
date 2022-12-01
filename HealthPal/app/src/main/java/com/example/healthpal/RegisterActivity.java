package com.example.healthpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthpal-1905e-default-rtdb.firebaseio.com/");

    EditText Fname, textEmail, textPassword, confirmPassword, userName;
    Button register3, login4;
    FirebaseAuth fireAuth;
    FirebaseFirestore fstore;
    boolean valid = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register3 = findViewById(R.id.register3);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        userName = findViewById(R.id.userName);
        Fname = findViewById(R.id.Fname);
        login4 = findViewById(R.id.login4);
        fireAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        login4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        });



        register3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = textEmail.getText().toString().trim();
                String fullname = Fname.getText().toString().trim();
                String passowrd = textPassword.getText().toString().trim();
                String password_confrim = confirmPassword.getText().toString().trim();
                String user_name = userName.getText().toString().trim();

                if(email.isEmpty() || passowrd.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please input all fields", Toast.LENGTH_SHORT).show();
                }

                else if(!password_confrim.equals(passowrd)){
                    Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }

                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(user_name.toString())){
                                Toast.makeText(RegisterActivity.this, "Username already in use", Toast.LENGTH_SHORT).show();



                            }

                            else{

                                databaseReference.child("users").child(user_name).child("fullname").setValue(fullname);
                                databaseReference.child("users").child(user_name).child("email").setValue(email);
                                databaseReference.child("users").child(user_name).child("password").setValue(passowrd);
                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);

                           }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }



            }
        });
    }
}






