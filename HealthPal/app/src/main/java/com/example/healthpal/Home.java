package com.example.healthpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements SelectListener {
    TextView user_name2;
    DatabaseReference reff;
    Button add;
    ArrayList<Medicine> list;
    MyAdapter adapter;
    RecyclerView recyclerView;

    ImageButton user_profile;
    DatabaseReference databaseReference ;
            //= FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthpal-1905e-default-rtdb.firebaseio.com/");

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Home.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user_name2 = findViewById(R.id.user_name2);
        user_profile = findViewById(R.id.user_profile);
        add = findViewById(R.id.add);
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        recyclerView = findViewById(R.id.recycler);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new MyAdapter(this, list);
        //recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Medicine");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list, this);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Medicine medicine = dataSnapshot.getValue(Medicine.class);
                    list.add(medicine);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




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


//
//







    }

    @Override
    public void onItemClicked(Medicine myMedicine) {
        Toast.makeText(this, myMedicine.getName() + " Quantity is now " + myMedicine.getQuantity(), Toast.LENGTH_SHORT).show();

    }
}