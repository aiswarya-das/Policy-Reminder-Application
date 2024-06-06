package com.example.policyreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
public class MainActivity extends AppCompatActivity{
    //private int notificationId = 1;

    //public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    //private final static String default_notification_channel_id = "default" ;
   // Button btnDate ;
    //TextView btnDate;
  //  final Calendar myCalendar = Calendar. getInstance () ;
    private FloatingActionButton mAddReminderButton;
    private Toolbar mToolbar;
    AlarmCursorAdapter mCursorAdapter;
    AlarmReminderDbHelper alarmReminderDbHelper = new AlarmReminderDbHelper(this);
    ListView reminderListView;
    ProgressDialog prgDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set onClick Listener
       // findViewById(R.id.setBtn).setOnClickListener(this);
        //findViewById(R.id.cancelBtn).setOnClickListener(this);
       // btnDate = findViewById(R.id.tvDate ) ;

        //boolean alarm = (PendingIntent.getBroadcast(this, 0, new Intent("ALARM"), PendingIntent.FLAG_NO_CREATE) == null);

        /*if(alarm){
            Intent itAlarm = new Intent("ALARM");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,itAlarm,0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 3);
            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60000, pendingIntent);
        }*/

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.app_name);


        reminderListView = (ListView) findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        reminderListView.setEmptyView(emptyView);

        mCursorAdapter = new AlarmCursorAdapter(this, null);
        reminderListView.setAdapter(mCursorAdapter);

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, AddReminderActivity.class);

                Uri currentVehicleUri = ContentUris.withAppendedId(AlarmReminderContract.AlarmReminderEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentVehicleUri);

                startActivity(intent);

            }
        });


        mAddReminderButton = (FloatingActionButton) findViewById(R.id.fab);

        mAddReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddReminderActivity.class);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(VEHICLE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                AlarmReminderContract.AlarmReminderEntry._ID,
                AlarmReminderContract.AlarmReminderEntry.KEY_TITLE,
                AlarmReminderContract.AlarmReminderEntry.KEY_DATE,
                AlarmReminderContract.AlarmReminderEntry.KEY_TIME,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE,
                AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE

        };

        return new CursorLoader(this,   // Parent activity context
                AlarmReminderContract.AlarmReminderEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }
  /* private void scheduleNotification (Notification notification , long delay) {
        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , delay , pendingIntent) ;
    }
   private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet (DatePicker view , int year , int monthOfYear , int dayOfMonth) {
            myCalendar .set(Calendar. YEAR , year) ;
            myCalendar .set(Calendar. MONTH , monthOfYear) ;
            myCalendar .set(Calendar. DAY_OF_MONTH , dayOfMonth) ;
            updateLabel() ;
        }
    };
   public void setDate (View view) {
        new DatePickerDialog(
                MainActivity. this, date ,
                myCalendar .get(Calendar. YEAR ) ,
                myCalendar .get(Calendar. MONTH ) ,
                myCalendar .get(Calendar. DAY_OF_MONTH )
        ).show() ;
    }
    private void updateLabel () {
        String myFormat = "dd/MM/yy" ; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
        Date date = myCalendar .getTime() ;
        btnDate .setText(sdf.format(date)) ;
        scheduleNotification(getNotification( btnDate .getText().toString()) , date.getTime()) ;
    }

    public void createNotification (View view) {
        Intent myIntent = new Intent(getApplicationContext() , NotifyService. class ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE ) ;
        PendingIntent pendingIntent = PendingIntent. getService ( this, 0 , myIntent , 0 ) ;
        Calendar calendar = Calendar. getInstance () ;
        calendar.set(Calendar. SECOND , 0 ) ;
        calendar.set(Calendar. MINUTE , 0 ) ;
        calendar.set(Calendar. HOUR , 0 ) ;
        calendar.set(Calendar. AM_PM , Calendar. AM ) ;
        calendar.add(Calendar. DAY_OF_MONTH , 1 ) ;
        alarmManager.setRepeating(AlarmManager. RTC_WAKEUP , calendar.getTimeInMillis() , 1000 * 60 * 60 * 24 , pendingIntent) ;
    }*/
 /* public void onClick(View view) {

      EditText editText = findViewById(R.id.editText);
      TimePicker timePicker = findViewById(R.id.timePicker);
      DatePicker datePicker = findViewById(R.id.datePicker);


      // Intent
      Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
      intent.putExtra("notificationId", notificationId);
      intent.putExtra("message", editText.getText().toString());

      // PendingIntent
      PendingIntent pendingIntent = PendingIntent.getBroadcast(
              MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
      );

      // AlarmManager
      AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

      switch (view.getId()) {
          case R.id.setBtn:
              int hour = timePicker.getCurrentHour();
              int minute = timePicker.getCurrentMinute();

             // int monthOfYear = timePicker.getCurrentMinute();
             // int dayOfMonth = timePicker.getCurrentMinute();
              DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet (DatePicker view , int year , int monthOfYear , int dayOfMonth) {
                      myCalendar .set(Calendar. YEAR , year) ;
                      myCalendar .set(Calendar. MONTH , monthOfYear) ;
                      myCalendar .set(Calendar. DAY_OF_MONTH , dayOfMonth) ;

                  }
              };

              // Create time.
              Calendar startTime = Calendar.getInstance();
              startTime.set(Calendar.HOUR_OF_DAY, hour);
              startTime.set(Calendar.MINUTE, minute);
              startTime.set(Calendar.SECOND, 0);
              startTime.set(Calendar. AM_PM , Calendar. AM ) ;
             // startTime.set(Calendar. YEAR , year) ;
             // startTime.set(Calendar. MONTH , monthOfYear) ;
             // startTime.set(Calendar. DAY_OF_MONTH , dayOfMonth);
              long alarmStartTime = startTime.getTimeInMillis();

              // Set Alarm
              alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
              Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
              break;

          case R.id.cancelBtn:
              alarmManager.cancel(pendingIntent);
              Toast.makeText(this, "Canceled.", Toast.LENGTH_SHORT).show();
              break;
      }*/
  }
