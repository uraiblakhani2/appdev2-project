package com.example.healthpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddMedicine extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthpal-1905e-default-rtdb.firebaseio.com/");
    ImageButton to_date;
    Button med_save;
    DatePickerDialog.OnDateSetListener setListener;
    TextView date2, med_name, med_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        to_date = findViewById(R.id.to_date);
        date2 = findViewById(R.id.date);
        med_save = findViewById(R.id.med_save);
        med_name = findViewById(R.id.med_name);
        med_quantity = findViewById(R.id.med_quantity);
        date2 = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(

                        AddMedicine.this, new DatePickerDialog.OnDateSetListener() {
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

        med_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String datte = date2.getText().toString();
                String med_name2 = med_name.getText().toString();
                String med_quantity2 = med_quantity.getText().toString();
                String id = databaseReference.push().getKey();

                Medicine medicine = new Medicine(datte,med_name2, med_quantity2);



                databaseReference.child("Medicine").child(id).setValue(medicine)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(AddMedicine.this, "Inserted successfully", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });


            }
        });


    }
}