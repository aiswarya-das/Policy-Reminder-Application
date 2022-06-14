package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationMessage extends AppCompatActivity {
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);
        tv = (TextView)findViewById(R.id.tv_message);
        Intent intent = getIntent();
        String msg =  intent.getStringExtra("message");
        String msg2 =  intent.getStringExtra("date");
        tv.setText(msg +" \nat " +msg2);
    }
}