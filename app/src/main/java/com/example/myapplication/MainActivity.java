package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Dialog;

public class MainActivity extends AppCompatActivity {
    ImageView imgHinh;
    private Button button1,button3,button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgHinh =findViewById(R.id.imageView);
        button1=findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent( MainActivity.this, LichHenActivity.class);
                startActivity(intent);
            }
        });

        /*button3=findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,chuonghengio.class);
                startActivity(intent);

            }
        });*/

        button4=findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DialogThoat();
            }
        });
    }

    private void DialogThoat() {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.exit);
        //Tắt click ngoài là tắt
        dialog.setCanceledOnTouchOutside(false);
        Button btnco = dialog.findViewById(R.id.btn_co);
        Button btnko = dialog.findViewById(R.id.btn_ko);

        btnco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                //Thoát
                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                intent1.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent1);
            }
        });

        //Nếu clik không thì đóng cửa số
        btnko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        //show dialog
        dialog.show();

    }


}