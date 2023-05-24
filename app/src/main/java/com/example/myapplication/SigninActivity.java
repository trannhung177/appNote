package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {
    EditText edtuser, edtPassword, edtTK, edtMK;
    Button btnDN, btnDKy, btnThoat, btnHuy, btnDongY;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtuser=findViewById(R.id.edtuser);
        edtPassword=findViewById(R.id.edtPassword);
        btnDN=findViewById(R.id.btnDN);
        btnDKy=findViewById(R.id.btnDKy);
        btnThoat=findViewById(R.id.btnThoat);


        DB = new DBHelper(this);
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtuser.getText().toString();
                String pass = edtPassword.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    Toast.makeText(SigninActivity.this,"All fields Required", Toast.LENGTH_SHORT).show();

                }else{
                    Boolean checkPass = DB.checkPassword(user, pass);
                    Log.e("Start", checkPass.toString());
                    if(checkPass == true){
                        Log.e("Second", checkPass.toString());
                        Toast.makeText(SigninActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SigninActivity.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }) ;
        btnDKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
    }

}
