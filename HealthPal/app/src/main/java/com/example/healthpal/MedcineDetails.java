package com.example.healthpal;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MedcineDetails extends AppCompatActivity {
    Button med_updates;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthpal-1905e-default-rtdb.firebaseio.com/");

    EditText med_quantity2, med_name2;
    ImageButton to_date;

    TextView date2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medcine_details);

        med_quantity2 = findViewById(R.id.med_quantity2);
        med_name2 = findViewById(R.id.med_name2);
        date2 = findViewById(R.id.date2);
        to_date = findViewById(R.id.to_date);

        med_updates = findViewById(R.id.done);

        String name = getIntent().getStringExtra("name");
        String date = getIntent().getStringExtra("date");
        String quantity = getIntent().getStringExtra("quantity");
        String userids = getIntent().getStringExtra("user");

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);




        med_quantity2.setText(quantity);
        med_name2.setText(name);
        date2.setText(date);




        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(

                        MedcineDetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        date2.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();


            }
        });


        med_updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference classicalMechanicsRef = rootRef.child("Medicine");
                Query query = classicalMechanicsRef.orderByChild("name").equalTo(name);

                Medicine medicine2 = new Medicine(date2.getText().toString(), med_name2.getText().toString(), med_quantity2.getText().toString());

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            ds.getRef().setValue(medicine2);

                            Toast.makeText(MedcineDetails.this, "Medicine data updated", Toast.LENGTH_SHORT).show();

                            Intent ione = new Intent(getApplicationContext(), Home.class);
                            ione.putExtra("user",userids);
                            startActivity(ione);



                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());
                    }
                };
                query.addListenerForSingleValueEvent(valueEventListener);

            }
        });




    }
}