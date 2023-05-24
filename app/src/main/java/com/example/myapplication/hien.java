package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class hien extends AppCompatActivity {
    TextView txtNoiDung, tgianthuc;
    @SuppressLint("MissingInFlatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hien);
        txtNoiDung = findViewById(R.id.textView5);
        tgianthuc = findViewById(R.id.timeA);

        Intent intent = getIntent();
        txtNoiDung.setText(intent.getStringExtra("noiDung"));
        tgianthuc.setText(intent.getStringExtra("thoigian"));

    }
}
