package com.example.baothuc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button btn_datgio, btn_dung;
    TextView txtView_hienthi;
    Calendar calendar;
    TimePicker timePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_datgio = findViewById(R.id.button_datgio);
        btn_dung = findViewById(R.id.button_dung);
        txtView_hienthi = findViewById(R.id.textView);
        timePicker = findViewById(R.id.TimePicker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Intent intent = new Intent(MainActivity.this,ArlamReceiver.class);

        btn_datgio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());

                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();

                String str_gio = String.valueOf(gio);
                String str_phut = String.valueOf(phut);

                intent.putExtra("extra","on");

                pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);


                txtView_hienthi.setText("Ban da dat gio: "+str_gio +" : "+str_phut);
            }
        });

        btn_dung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtView_hienthi.setText("Dung lai");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra","off");
                sendBroadcast(intent);
            }
        });

    }
}
