package com.sjbit.ereport.main;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sjbit.ereport.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

/**
 * Main Activity of the Application.
 */
public class HomeActivity extends AppCompatActivity {

	public static BottomNavigationView navView;
	public final static String default_notification_channel_id = "default" ;
	public static String NOTIFICATION_CHANNEL_ID ="10001";
	public static final Calendar myCalendar = Calendar.getInstance() ;
	public static final Calendar morningCalendar = Calendar.getInstance();
	public static final Calendar afternoonCalendar = Calendar.getInstance();
	public static final Calendar eveningCalendar = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		//Setup Navigation.
		navView = findViewById(R.id.nav_view);
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
				R.id.navigation_home,
				R.id.navigation_hospitals,
				R.id.navigation_upload,
				R.id.navigation_documents,
				R.id.navigation_profile
		).build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);
		updateLabel();
		Log.d(TAG, "HomeActivity:updateLabel Called() ");
	}

	private void scheduleNotification(Notification notification, long delay){
		morningCalendar.setTimeInMillis(System.currentTimeMillis());
		morningCalendar.set(Calendar.HOUR_OF_DAY, 10);
		morningCalendar.set(Calendar.MINUTE, 0);
		morningCalendar.set(Calendar.SECOND, 0);

		afternoonCalendar.setTimeInMillis(System.currentTimeMillis());
		afternoonCalendar.set(Calendar.HOUR_OF_DAY, 14);
		afternoonCalendar.set(Calendar.MINUTE, 0);
		afternoonCalendar.set(Calendar.SECOND, 0);

		eveningCalendar.setTimeInMillis(System.currentTimeMillis());
		eveningCalendar.set(Calendar.HOUR_OF_DAY, 19);
		eveningCalendar.set(Calendar.MINUTE, 43);
		eveningCalendar.set(Calendar.SECOND, 15);

		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent notificationIntent = new Intent( this, MyNotificationPublisher.class ) ;
		notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID , 1 ) ;
		notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION , notification) ;
		Log.d("TIME", myCalendar.getTime().toString() +  eveningCalendar.getTime().toString());
		PendingIntent pendingIntent = PendingIntent.getBroadcast ( this, 0 , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT ) ;
		manager.setRepeating(AlarmManager.RTC_WAKEUP, morningCalendar.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY, pendingIntent);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, afternoonCalendar.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY, pendingIntent);
		manager.setRepeating(AlarmManager.RTC_WAKEUP, eveningCalendar.getTimeInMillis(),
				AlarmManager.INTERVAL_DAY, pendingIntent);
		Log.d(TAG, "Scheduled Notification");
	}
	private Notification getNotification (String content) {
		Log.d(TAG, "GetNotification");
		NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
		builder.setContentTitle("Dosage Notification") ;
		builder.setContentText(content) ;
		builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
		builder.setAutoCancel(true) ;
		builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
		return builder.build() ;
	}

	private void updateLabel () {
		String myFormat = "dd/MM/yy" ; //In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
		Date date = myCalendar.getTime();
		scheduleNotification(getNotification( "") , date.getTime()) ;
	}
}