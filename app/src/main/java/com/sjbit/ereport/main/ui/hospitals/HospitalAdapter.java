package com.sjbit.ereport.main.ui.hospitals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjbit.ereport.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Custom RecyclerView Adapter to set Hospital Data to corresponding View.
 */
public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    ArrayList<String> hospitals = new ArrayList<>();

    HospitalAdapter(HashSet<String> hospital) {
        hospitals.addAll(hospital);
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_hospitals, parent, false);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        holder.nameTextView.setText(hospitals.get(position));
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    public static class HospitalViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
        }
    }
}
