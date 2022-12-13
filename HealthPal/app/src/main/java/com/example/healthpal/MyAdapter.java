package com.example.healthpal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
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

        Medicine medicine = list.get(position);
        holder.name.setText(medicine.getName());
        holder.date.setText(medicine.getDate());
        holder.quantity.setText(medicine.getQuantity());

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quant = Integer.valueOf(medicine.getQuantity());
                quant++;
                holder.quantity.setText(String.valueOf(quant));
                medicine.setQuantity(String.valueOf(quant));
                listener.onItemClicked(medicine);
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int quant = Integer.valueOf(medicine.getQuantity());
                quant--;
                holder.quantity.setText(String.valueOf(quant));
                medicine.setQuantity(String.valueOf(quant));
                listener.onItemClicked(medicine);

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, date, quantity;
        ImageView plus, minus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            name = itemView.findViewById(R.id.medicine_name);
            date = itemView.findViewById(R.id.medicine_date);
            quantity = itemView.findViewById(R.id.medicine_quantity);
        }
    }
}