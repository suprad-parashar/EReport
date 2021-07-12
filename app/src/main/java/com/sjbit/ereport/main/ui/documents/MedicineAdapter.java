package com.sjbit.ereport.main.ui.documents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjbit.ereport.R;
import com.sjbit.ereport.storage.Medicine;

import java.util.ArrayList;

/**
 * Custom RecyclerView Adapter to set Hospital Data to corresponding View.
 */
public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

	ArrayList<Medicine> medicines = new ArrayList<>();

	MedicineAdapter(ArrayList<Medicine> medicines) {
		this.medicines = medicines;
	}

	@NonNull
	@Override
	public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_medicine, parent, false);
		return new MedicineViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
		Medicine medicine = medicines.get(position);
		holder.medicineNameTextView.setText(medicine.getMedicineName());
		String medicineDose = medicine.getMorningDose() + " - " + medicine.getAfternoonDose() + " - " + medicine.getEveningDose();
		holder.medicineDoseTextView.setText(medicineDose);
	}

	@Override
	public int getItemCount() {
		return medicines.size();
	}

	public static class MedicineViewHolder extends RecyclerView.ViewHolder {
		private TextView medicineNameTextView;
		private TextView medicineDoseTextView;

		public MedicineViewHolder(@NonNull View itemView) {
			super(itemView);
			medicineNameTextView = itemView.findViewById(R.id.medicine_name);
			medicineDoseTextView = itemView.findViewById(R.id.medicine_dose);
		}
	}
}
