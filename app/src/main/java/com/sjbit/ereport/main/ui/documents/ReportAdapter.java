package com.sjbit.ereport.main.ui.documents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjbit.ereport.R;
import com.sjbit.ereport.storage.Report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Custom RecyclerView Adapter to set Hospital Data to corresponding View.
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

	ArrayList<Report> docs = new ArrayList<>();

	ReportAdapter(ArrayList<Report> docs) {
		this.docs = docs;
	}

	@NonNull
	@Override
	public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_report_doc, parent, false);
		return new ReportViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Report object = (Report) docs.get(position);
		holder.reportNameTextView.setText(object.getTestName());
		holder.hospitalTextView.setText(object.getHospitalName());
		holder.dateTextView.setText(formatter.format(object.getDate()));
	}

	@Override
	public int getItemCount() {
		return docs.size();
	}

	public static class ReportViewHolder extends RecyclerView.ViewHolder {
		private TextView hospitalTextView;
		private TextView reportNameTextView;
		private TextView dateTextView;

		public ReportViewHolder(@NonNull View itemView) {
			super(itemView);
			reportNameTextView = itemView.findViewById(R.id.report_name);
			hospitalTextView = itemView.findViewById(R.id.hospital_name);
			dateTextView = itemView.findViewById(R.id.date);
		}
	}
}
