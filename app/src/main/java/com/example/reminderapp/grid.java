package com.example.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class grid extends AppCompatActivity {
    CardView cci,ti,pi,li,mi,health,content,pet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        cci = (CardView) findViewById(R.id.critical);
        pi = (CardView)findViewById(R.id.property);
        li = (CardView)findViewById(R.id.life);
        ti = (CardView)findViewById(R.id.travel);
        mi = (CardView)findViewById(R.id.motor);
        health = (CardView)findViewById(R.id.health);
        content = (CardView)findViewById(R.id.contents);
        pet = (CardView)findViewById(R.id.pet);
        cci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Reminder.class);
                intent.putExtra("title","Critical care insurance");
                startActivity(intent);
            }
        });
        pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Reminder.class);
                intent.putExtra("title","Property Insurance");
                startActivity(intent);
            }
        });
        li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Reminder.class);
                intent.putExtra("title","Life Insurance");
                startActivity(intent);
            }
        });
        ti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Reminder.class);
                intent.putExtra("title","Travel Insurance");
                startActivity(intent);
            }
        });
        mi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Reminder.class);
                intent.putExtra("title","Motor Insurance");
                startActivity(intent);
            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Reminder.class);
                intent.putExtra("title","Health insurance");
                startActivity(intent);
            }
        });
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Reminder.class);
                intent.putExtra("title","Contents insurance");
                startActivity(intent);
            }
        });
         pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Reminder.class);
                intent.putExtra("title","Pet insurance");
                startActivity(intent);
            }
        });
    }
}