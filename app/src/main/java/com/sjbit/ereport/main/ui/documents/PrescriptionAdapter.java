package com.sjbit.ereport.main.ui.documents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjbit.ereport.R;
import com.sjbit.ereport.storage.Prescription;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Custom RecyclerView Adapter to set Hospital Data to corresponding View.
 */
public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.DocumentViewHolder> {

	ArrayList<Prescription> docs = new ArrayList<>();

	PrescriptionAdapter(ArrayList<Prescription> docs) {
		this.docs = docs;
	}

	@NonNull
	@Override
	public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_prescription_doc, parent, false);
		return new DocumentViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Prescription object = (Prescription) docs.get(position);
		holder.doctorNameTextView.setText(object.getDoctorName());
		holder.dateTextView.setText(formatter.format(object.getDate()));

	}

	@Override
	public int getItemCount() {
		return docs.size();
	}

	public static class DocumentViewHolder extends RecyclerView.ViewHolder {
		private TextView doctorNameTextView;
		private TextView dateTextView;

		public DocumentViewHolder(@NonNull View itemView) {
			super(itemView);
			doctorNameTextView = itemView.findViewById(R.id.doctor_name);
			dateTextView = itemView.findViewById(R.id.date);
		}
	}
}
