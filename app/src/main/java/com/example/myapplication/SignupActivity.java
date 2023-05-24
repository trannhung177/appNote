package com.example.myapplication;

import static com.example.myapplication.DBHelper.UserName;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText edtuser,  edtPassword, edtPassword2;
    Button signup, singin, btnThoat;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtuser = findViewById(R.id.edtuser);
        edtPassword = findViewById(R.id.edtPassword);
        edtPassword2=findViewById(R.id.edtPassword2);
        signup = findViewById(R.id.signup);
        singin = findViewById(R.id.signin);
        DB= new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user = edtuser.getText().toString();
                String pass = edtPassword.getText().toString();
                String repass = edtPassword2.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)){
                    Toast.makeText(SignupActivity.this,"All fields Requires", Toast.LENGTH_SHORT).show();
                } else{
                    if(pass.equals(repass)){
                        Boolean checkUser = DB.checkUserName(UserName);
                        if(checkUser == false){
                            Boolean insert = DB.insertDatas(user,pass, repass);
                            if(insert == true){
                                Toast.makeText(SignupActivity.this,"Register Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(SignupActivity.this,"Resgistration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(SignupActivity.this,"User already Exists", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(SignupActivity.this,"Password are not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}
