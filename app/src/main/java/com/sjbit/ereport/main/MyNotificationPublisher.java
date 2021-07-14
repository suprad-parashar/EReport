package com.sjbit.ereport.main;

import android.app.Notification ;
import android.app.NotificationChannel ;
import android.app.NotificationManager ;
import android.content.BroadcastReceiver ;
import android.content.Context ;
import android.content.Intent ;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sjbit.ereport.R;
import com.sjbit.ereport.adapters.MyActivitiesAdapter;
import com.sjbit.ereport.main.HomeActivity;
import com.sjbit.ereport.object.Activity;
import com.sjbit.ereport.object.DateFormatConverter;
import com.sjbit.ereport.object.Medicine;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static com.sjbit.ereport.main.HomeActivity.eveningCalendar;
import static com.sjbit.ereport.main.HomeActivity.morningCalendar;
import static com.sjbit.ereport.main.HomeActivity.myCalendar;

public class MyNotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_CHANNEL_ID ="10001";
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification";
    public static ArrayList<Medicine> listOfMedicines = new ArrayList<>();
    public static Notification generatedNotification = null;
    public static boolean isNotify = false;
    public String medicine = "";

    public void onReceive (Context context , Intent intent) {
        Log.d("Notification Publisher", "OnReceive Called");
        if(myCalendar.getTime().equals(eveningCalendar.getTime())){
            getDataFromFirebase("Evening",context,intent);
        }else if(myCalendar.getTime().equals(morningCalendar.getTime())){
            getDataFromFirebase("Afternoon",context,intent);
        }else{
            getDataFromFirebase("Morning",context,intent);
        }
//        if (generatedNotification != null){
//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
//            Notification notification = intent.getParcelableExtra(NOTIFICATION) ;
//            if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
//                int importance = NotificationManager. IMPORTANCE_HIGH ;
//                NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
//                assert notificationManager != null;
//                notificationManager.createNotificationChannel(notificationChannel) ;
//            }
//            int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
//            assert notificationManager != null;
//            notificationManager.notify(id , notification) ;
//        }
    }

    public void getDataFromFirebase(String time,Context context,Intent intent){
        Log.d("Notification Publisher", "getDataFromFirebase called");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .child("blockchain");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String current_block = "Block ";
                long index = dataSnapshot.getChildrenCount() - 1;
                while (index >= 0) {
                    DataSnapshot blockObjectSnapshot = dataSnapshot.child(current_block + String.valueOf(index)).child("blockObject");
                    String type = blockObjectSnapshot.child("type").getValue().toString();
                    if (type.equals("PRESCRIPTION")) {
                        Log.d("Firebase","Found Prescription");
                        DataSnapshot medicineSnapshot = blockObjectSnapshot.child("medicines");
                        for (int i = 0; i < medicineSnapshot.getChildrenCount(); i++) {
                            DataSnapshot medicine = medicineSnapshot.child(String.valueOf(i));
                            listOfMedicines.add(new Medicine(medicine.child("medicineName").getValue().toString(), medicine.child("morningDose").getValue().toString(), medicine.child("afternoonDose").getValue().toString(), medicine.child("eveningDose").getValue().toString()));
                        }
                        break;
                    }
                    index--;
                }
                if(listOfMedicines.size() > 0){
                    isNotify = true;
                    Medicine m;
                    for (int i=0;i<listOfMedicines.size();i++){
                        m = listOfMedicines.get(i);
                        if (time.equals("Morning") && !m.getMorningDose().equals("0")){
                            medicine += m.getMedicineName() + " " +   m.getMorningDose() + "tablet ";
                        } else if (time.equals("Evening") && !m.getEveningDose().equals("0")) {
                            medicine += m.getMedicineName() +  " "  + m.getMorningDose() + "tablet ";
                        }else if (!m.getAfternoonDose().equals("0")){
                            medicine += m.getMedicineName() +  " " + m.getAfternoonDose() + "tablet ";
                        }


                    }
                    getNotification(medicine);
                    createNotification(context,intent);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void getNotification (String content) {
        Log.d("Notification Publisher", "GetNotification called");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(HomeActivity.navView.getContext(), HomeActivity.default_notification_channel_id ) ;
        builder.setContentTitle("Dosage Notification") ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel(true);
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        generatedNotification = builder.build() ;
    }

    private void createNotification(Context context,Intent intent){
        Log.d("Notification Publisher", "CreateNotification called");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE ) ;
        Notification notification = generatedNotification;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
        assert notificationManager != null;
        notificationManager.notify(id , notification) ;
    }
}
