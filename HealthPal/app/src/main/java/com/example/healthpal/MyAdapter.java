package com.example.healthpal;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthpal-1905e-default-rtdb.firebaseio.com/");

    Context context;
    ArrayList<Medicine> list;
    private SelectListener listener;




    public MyAdapter(Context context, ArrayList<Medicine> list, SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.medicines, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Medicine medicine = list.get(position);
        holder.name.setText(medicine.getName());
        //holder.date.setText(medicine.getDate());
        holder.quantity.setText(medicine.getQuantity());

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.valueOf(medicine.getQuantity());
                quant++;
                holder.quantity.setText(String.valueOf(quant));
                medicine.setQuantity(String.valueOf(quant));
                listener.onItemClicked(medicine);

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference classicalMechanicsRef = rootRef.child("Medicine");
                Query query = classicalMechanicsRef.orderByChild("name").equalTo(medicine.getName());
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            ds.child("quantity").getRef().setValue(medicine.getQuantity());
                            list.clear();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage()); //Don't ignore errors!
                    }
                };
                query.addListenerForSingleValueEvent(valueEventListener);



            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quant = Integer.valueOf(medicine.getQuantity());
                if (quant == 0) {
                    listener.onItemClicked2();

                } else {
                    quant--;

                    holder.quantity.setText(String.valueOf(quant));
                    medicine.setQuantity(String.valueOf(quant));
                    listener.onItemClicked(medicine);


                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference classicalMechanicsRef = rootRef.child("Medicine");
                    Query query = classicalMechanicsRef.orderByChild("name").equalTo(medicine.getName());
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                ds.child("quantity").getRef().setValue(medicine.getQuantity());
                                list.clear();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, databaseError.getMessage());
                        }
                    };
                    query.addListenerForSingleValueEvent(valueEventListener);


                }
            }
        });








        holder.delete_medic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference classicalMechanicsRef = rootRef.child("Medicine");
                Query query = classicalMechanicsRef.orderByChild("name").equalTo(medicine.getName());

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            ds.getRef().removeValue();
                            list.clear();

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




        holder.medicineId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked3(medicine);
 }
        });


        holder.alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked4(medicine);
            }
        });





    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,  quantity;
        ImageView plus, minus, delete_medic, medicineId, alarm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            medicineId = itemView.findViewById(R.id.medicineId);
            name = itemView.findViewById(R.id.medicine_name);
            delete_medic = itemView.findViewById(R.id.delete_medic);
            alarm = itemView.findViewById(R.id.alarm);
            quantity = itemView.findViewById(R.id.medicine_quantity);
        }
    }
}