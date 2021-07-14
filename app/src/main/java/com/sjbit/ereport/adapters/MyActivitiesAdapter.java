package com.sjbit.ereport.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sjbit.ereport.R;
import com.sjbit.ereport.object.Activity;

import java.util.ArrayList;
import java.util.Objects;

public class MyActivitiesAdapter extends RecyclerView.Adapter<MyActivitiesAdapter.MyActivitiesHolder> {

    //Declare Data Variables
    private Context context;
    private ArrayList<Activity> activities;
    private boolean isList;

    public MyActivitiesAdapter(Context context, ArrayList<Activity> activities, boolean isList) {
        this.context = context;
        this.activities = activities;
        this.isList = isList;
    }

    @NonNull
    @Override
    public MyActivitiesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_activity, parent, false);
        return new MyActivitiesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyActivitiesHolder holder, final int position) {
        final Activity activity = activities.get(position);

        //Check if Data presentation is a list or card.
//        if (!isList) {
//            holder.mainLayout.setLayoutParams(new ViewGroup.LayoutParams(750, ViewGroup.LayoutParams.MATCH_PARENT));
//            holder.mainLayout.setPadding(16, 0, 16, 0);
//        }
//        else {
//            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(32, 16, 32, 16);
//            holder.mainLayout.setLayoutParams(params);
//        }

        if (activity == null) {
            Log.e("ALPHA", "ROMEO");
        } else {
            holder.type.setText(activity.getType());
            holder.date.setText(activity.getDate());
            holder.doctorName.setText(activity.getDoctorName());
            if(activity.getType().equals("REPORT")) {
                holder.hospitalName.setText(activity.getHospitalName());
            }else{
                holder.hospitalName.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    static class MyActivitiesHolder extends RecyclerView.ViewHolder {
        TextView doctorName, date,type,hospitalName;

        MyActivitiesHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.doctor_name);
            hospitalName = itemView.findViewById(R.id.hospital_name);
            type = itemView.findViewById(R.id.type);
            date = itemView.findViewById(R.id.date);
        }
    }
}
