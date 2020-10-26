package com.example.alarme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private ToggleButton toggleButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timepicker);
        toggleButton = findViewById(R.id.togglebtn);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    Toast.makeText(MainActivity.this, "Adicionado", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MainActivity.this, Alarm.class);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);

                    Calendar calendario = Calendar.getInstance();
                    calendario.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendario.set(Calendar.MINUTE, timePicker.getHour());

                    long tempo = calendario.getTimeInMillis() - (calendario.getTimeInMillis() % 60000);

                    if (System.currentTimeMillis() > tempo) {
                        if (Calendar.AM_PM == 0) {
                            tempo = tempo + (1000 * 60 * 60 * 12);
                        } else {
                            tempo = tempo + (1000 * 60 * 60 * 24);
                        }
                    }
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tempo,0, pendingIntent);
                }

                else {
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this, "Excluido", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
