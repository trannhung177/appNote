package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class addLichHenActivity2 extends AppCompatActivity {
    Button btnluu,btndong,btnChonNgay;
    EditText tieude;
    EditText noidung;
    TextView txtNgayCongViec,txtTime, tgianthuc;

    String keySearch="";
    @SuppressLint("MissingInFlatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichhen);
        btnluu=findViewById(R.id.button);
        btndong=findViewById(R.id.button2);
        tieude=findViewById(R.id.tieude);
        noidung=findViewById(R.id.noidug);

        txtNgayCongViec=findViewById(R.id.txtNgayCongViec);
        txtTime = findViewById(R.id.inpTime);
        tgianthuc = findViewById(R.id.tgianthuc);






        txtNgayCongViec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar =Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog (addLichHenActivity2.this,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar chooseDate = Calendar.getInstance();
                                chooseDate.set(i,i1,i2);
                                String strDate = simpleDateFormat.format(chooseDate.getTime());
                                txtNgayCongViec.setText(strDate);
                            }
                        },year,month,day
                );
                datePickerDialog.show();
            }
        });

        Intent Rintent=getIntent();
        String Action= Rintent.getAction();
        if(Action=="update"){
            GhiChu ghiChu=(GhiChu) Rintent.getExtras().get("ghiChu");
            keySearch=ghiChu.getTieude();
            tieude.setText(ghiChu.getTieude());
            noidung.setText(ghiChu.getNoidung());

        }

        btnluu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int tieudelengh= tieude.getText().toString().length();
                int noidungLenght =  noidung.getText().toString().length();
                int txtNgayCongViecText = txtNgayCongViec.getText().toString().length();
                int txtTimeLenght = txtTime.getText().toString().length();
                int txttgianthuc = tgianthuc.getText().toString().length();
                if(tieudelengh > 50) {
                    Toast.makeText(addLichHenActivity2.this,"không quá 50 kí tự ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(tieudelengh <1) {
                    Toast.makeText(addLichHenActivity2.this,"Tiêu đề không được để trống",Toast.LENGTH_LONG).show();
                    return;
                }
                if (noidungLenght <1) {
                    Toast.makeText(addLichHenActivity2.this,"Nội dung không được để trống",Toast.LENGTH_LONG).show();
                    return;
                }
                if (txtNgayCongViecText == 0 ) {
                    Toast.makeText(addLichHenActivity2.this,"Hãy nhập ngày",Toast.LENGTH_LONG).show();
                    return;
                }
                if (txtTimeLenght == 0 ) {
                    Toast.makeText(addLichHenActivity2.this,"Hãy nhập thời gian",Toast.LENGTH_LONG).show();
                    return;
                }
                GhiChuDB db = new GhiChuDB(addLichHenActivity2.this);
                if (db.checkNgayThang(txtNgayCongViec.getText().toString(),txtTime.getText().toString())) {
                    Toast.makeText(addLichHenActivity2.this,"Đã có công việc trong thời gian này",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intentLuu = new Intent();
                intentLuu.putExtra("tieude", tieude.getText().toString());
                intentLuu.putExtra("ngaythang", txtNgayCongViec.getText().toString());
                intentLuu.putExtra("noidung", noidung.getText().toString());
                intentLuu.putExtra("gio", txtTime.getText().toString());
                intentLuu.putExtra("thoigian", tgianthuc.getText().toString());

                if (Action == "update") {
                    intentLuu.putExtra("key", keySearch);

                }
                setResult(RESULT_OK, intentLuu);

                scheduleJob();

                finish();
            }
        });

        btndong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tgianthuc.setText(
                new SimpleDateFormat("EEEE,dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );
    }

//    private void initTime() {
//        Date date = new Date();
//        txtTime.setText(date.getHours() + ":" + date.getMinutes());
//        txtNgayCongViec.setText(date.getDay() + "/" + date.getMonth() + "/" +  (date.getYear() + 1900)); // +1900 do getYear trả về năm hiện tại -1900
//    }


    public void pickTime(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(addLichHenActivity2.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                txtTime.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void scheduleJob() {
        try {
            AlarmManager alarmMgr = (AlarmManager) getSystemService(addLichHenActivity2.this.ALARM_SERVICE);
            Intent intent2 = new Intent(addLichHenActivity2.this, ALarmReceiver.class);
            intent2.putExtra("title", tieude.getText().toString());
            intent2.putExtra("content", noidung.getText().toString());
            intent2.putExtra("type", "calendar_reminder");

//            PendingIntent alarmIntent = PendingIntent.getBroadcast(MainActivity2.this, 0, intent2, 0);

            //Sửa từ 0 qua PendingIntent.FLAG_UPDATE_CURRENT để thông báo update đc dữ liệu mới nhất
            PendingIntent alarmIntent = PendingIntent.getBroadcast(addLichHenActivity2.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

            String sDate1= txtNgayCongViec.getText().toString() + " " + txtTime.getText().toString();


            Date date = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(sDate1);

            //Muốn thông báo chạy luôn thì chạy cái bên dưới
//            alarmMgr.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                    date.getTime() - new Date().getTime(), alarmIntent);

            //Thông báo chạy đúng thời gian đã chỉnh
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP,
                    date.getTime(), alarmIntent);

        } catch (ParseException e) {
            Toast.makeText(this,"Khong dung dinh dang ngay gio", Toast.LENGTH_LONG).show();
        }

    }
}
