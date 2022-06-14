package com.example.reminderapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mCreateRem;
    RecyclerView mRecyclerview;
    ArrayList<Model> dataholder = new ArrayList<Model>();                                               //Array list to add reminders and display in recyclerview
    myAdapter adapter;
    dbManager dbManager = new dbManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mCreateRem = (FloatingActionButton) findViewById(R.id.create_reminder);                     //Floating action button to change activity
        mCreateRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), grid.class);
                startActivity(intent);                                                              //Starts the new activity to add Reminders
            }
        });

        Cursor cursor = new dbManager(getApplicationContext()).readallreminders();                  //Cursor To Load data From the database
        while (cursor.moveToNext()) {
            Model model = new Model(cursor.getString(1), cursor.getString(2), cursor.getString(3));

            dataholder.add(model);
        }
        adapter = new myAdapter(dataholder);
        mRecyclerview.setAdapter(adapter);                                                          //Binds the adapter with recyclerview
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerview);
    }
    public void cancelAlarm(){
         Log.i("Cancel","Inside cancel Alarm");
        Intent intent = new Intent(getApplicationContext(), NotificationMessage.class);
        AlarmManager am=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.cancel(pi);
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
             int position = viewHolder.getAdapterPosition();
            switch (direction)
            {
                case ItemTouchHelper.LEFT:
                    //cancelAlarm();
                    dbManager.deleteTask(dataholder.get(position).getTitle());
                    dataholder.remove(position);

                    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                   //assigning alarm manager object to set alarm
                    Intent intent = new Intent(getApplicationContext(), AlarmBroadcast.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                    am.cancel(pendingIntent);
                    adapter.notifyItemRemoved(position);
                    break;
            }
        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    @Override
    public void onBackPressed() {
        finish();                                                                                   //Makes the user to exit from the app
        super.onBackPressed();
    }
}