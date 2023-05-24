package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class intent extends AppCompatActivity {
    Button btnLuu;
    EditText etNoiDung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        btnLuu = findViewById(R.id.button);
        etNoiDung = findViewById(R.id.noidug);

        btnLuu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(intent.this, hien.class);
                intent1.putExtra("noiDung", etNoiDung.getText().toString());
                startActivity(intent1);
                finish();
            }
        });
    }
}
