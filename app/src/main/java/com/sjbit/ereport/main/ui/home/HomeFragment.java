package com.sjbit.ereport.main.ui.home;

import com.sjbit.ereport.adapters.MyActivitiesAdapter;
import com.sjbit.ereport.object.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sjbit.ereport.R;
import com.sjbit.ereport.object.DateFormatConverter;
import com.sjbit.ereport.storage.Block;
import com.sjbit.ereport.object.Medicine;
import com.sjbit.ereport.storage.Prescription;
import com.sjbit.ereport.storage.Report;
import com.sjbit.ereport.storage.Type;
import com.sjbit.ereport.storage.Uploader;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Handles the Main Page of the User's Dashboard.
 */
public class HomeFragment extends Fragment {

    TextView emptyView;
    LinearLayout activitiesLayout;
    RecyclerView homeActivitiesRecyclerView;

    private static final FirebaseAuth auth = FirebaseAuth.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        //Initialise UI Variables
        emptyView = view.findViewById(R.id.empty_view);
        activitiesLayout = view.findViewById(R.id.activities_layout);

        TextView userView = view.findViewById(R.id.welcome_user);
        String welcomeMessage = "Welcome, " + Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getDisplayName()).split(" ")[0] + "!";
        userView.setText(welcomeMessage);

        //Setup Recycler View.
        homeActivitiesRecyclerView = view.findViewById(R.id.home_cards_list);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        homeActivitiesRecyclerView.setLayoutManager(manager);

        //Set Activities Data.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("blockchain");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Activity> activities = new ArrayList<>();
                String current_block = "Block ";
                long index = dataSnapshot.getChildrenCount() - 1;
                int count = 3;
                if (index == -1) {
                    activitiesLayout.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                while (index >= 0 && count > 0) {
                    DataSnapshot blockObjectSnapshot = dataSnapshot.child(current_block + String.valueOf(index)).child("blockObject");
                    DataSnapshot dateSnapshot = blockObjectSnapshot.child("date");
                    DateFormatConverter date = new DateFormatConverter(dateSnapshot.child("date").getValue().toString(), dateSnapshot.child("month").getValue().toString(), dateSnapshot.child("year").getValue().toString());

                    String type = blockObjectSnapshot.child("type").getValue().toString();
                    String doctorName = blockObjectSnapshot.child("doctorName").getValue().toString();
                    if (type.equals("REPORT")) {
                        String data = blockObjectSnapshot.child("data").getValue().toString();
                        String hospitalName = blockObjectSnapshot.child("hospitalName").getValue().toString();
                        String testName = blockObjectSnapshot.child("testName").getValue().toString();
                        activities.add(new Activity(type, doctorName, hospitalName, testName,date.getDateInFormat()));
                    } else {
						DataSnapshot medicineSnapshot = blockObjectSnapshot.child("medicines");
						ArrayList<Medicine> listOfMedicines = new ArrayList<>();
						for (int i = 0; i < medicineSnapshot.getChildrenCount(); i++) {
							DataSnapshot medicine = medicineSnapshot.child(String.valueOf(i));
							listOfMedicines.add(new Medicine(medicine.child("medicineName").getValue().toString(), medicine.child("morningDose").getValue().toString(), medicine.child("afternoonDose").getValue().toString(), medicine.child("eveningDose").getValue().toString()));
						}
                        activities.add(new Activity(type, doctorName, listOfMedicines,date.getDateInFormat()));
                    }
                    count--;
                    index--;
                }
				homeActivitiesRecyclerView.setAdapter(new MyActivitiesAdapter(requireContext(), activities, false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//		userView.setOnClickListener(view1 -> {
//			Toast.makeText(requireContext(), "TOUCH", Toast.LENGTH_LONG).show();
////			Report report = new Report(new Date(), "Dr. ABC", "Urine Routine", "Kanva Laboratories", "WBC: 16\nRBC: 0");
//			ArrayList<Medicine> medicines = new ArrayList<>();
//			Medicine medicine = new Medicine("Dolo 650", 1, 0, 1);
//			medicines.add(medicine);
//			medicine = new Medicine("Fabiflu", 2, 2, 2);
//			medicines.add(medicine);
//			Prescription prescription = new Prescription(new Date(), "Srivalli", medicines);
//			Block block = new Block(Type.PRESCRIPTION, prescription);
//			Uploader.addBlock(block);
//		});
    }
}