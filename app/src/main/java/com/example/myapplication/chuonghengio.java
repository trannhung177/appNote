package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.media.Ringtone;
import android.media.RingtoneManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class chuonghengio extends AppCompatActivity {
    Button btnHenGio, btnDungLai;
    TextView txtHienThi;
    android.widget.TimePicker TimePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuonghengio);

        btnHenGio = (Button) findViewById(R.id.btnHenGio);
        btnDungLai = (Button) findViewById(R.id.btnDungLai);
        txtHienThi = (TextView) findViewById(R.id.txtHienThi);
        TimePicker = (android.widget.TimePicker) findViewById(R.id.TimePicker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Intent intent = new Intent(chuonghengio.this, ALarmReceiver.class);
        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY, TimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, TimePicker.getCurrentMinute());

                int gio = TimePicker.getCurrentHour();
                int phut = TimePicker.getCurrentMinute();

                String string_gio = String.valueOf(gio);
                String string_phut = String.valueOf(phut);

                if (gio > 12) {
                    string_gio = String.valueOf(gio - 12);
                }
                if (phut < 10) {
                    string_phut = "0" + String.valueOf(phut);
                }
                intent.putExtra("extra", "on");
                pendingIntent = PendingIntent.getBroadcast(chuonghengio.this, 0, intent, pendingIntent.FLAG_UPDATE_CURRENT
                );
                alarmManager.set(alarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                txtHienThi.setText("Giờ bạn đặt là " + string_gio + ":" + string_phut);
            }
        });

        btnDungLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHienThi.setText("Dừng lại");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra", "off");
                sendBroadcast(intent);
            }
        });

    }
}
