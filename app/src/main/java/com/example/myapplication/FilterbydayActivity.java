package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class FilterbydayActivity extends AppCompatActivity {
    private static final int BEFORE_DAY_Calendar_id = 1;
    private static final int AFTER_DAY_Calendar_id =2;
    private static final int IN_DAY_Calendar_id = 3;

    public static final int BEFORE_DAY_RESULT_CODE = 4;
    public static final int AFTER_DAY_RESULT_CODE =5;
    public static final int IN_DAY_RESULT_CODE =6;

    public static final String BEFORE_DAY_RESULT_KEY = "before_day";
    public static final String AFTER_DAY_RESULT_KEY = "after_day";
    public static final String IN_DAY_RESULT_KEY = "inRange";

    private TextView before_day, after_day, inRangeDay;
    DatePickerDialog.OnDateSetListener tv_add_before_day_listener, tv_add_after_day_listener;

    private void getViews() {
        this.before_day = findViewById(R.id.tv_beforeDay);
        this.after_day = findViewById(R.id.tv_afterDay);
        this.inRangeDay = findViewById(R.id.tv_inRangeDay);
    }
    private void createDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,listener,  year, month, day);
        datePickerDialog.show();
    }

    private Dialog BeforeDateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.activity_filterbyday, null);
        View view = inflater.inflate(R.layout.datefilterbyday, null);
        TextView tv_title = (TextView) view.findViewById(R.id.filter_date_tv_title);
        TextView tv_date = (TextView) view.findViewById(R.id.filter_date_tv_date);

        tv_title.setText("Trước");
        tv_date.setText("Nhấn để chọn");
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDatePickerDialog(FilterbydayActivity.this, tv_add_before_day_listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int nam, int thang, int ngay) {
//                                                   tv_date.setText(String.valueOf(i) + ":" + String.valueOf(i1));
                        //+1 tháng do tháng tính từ 0->11
                        tv_date.setText(String.valueOf(ngay) + "/" + String.valueOf(thang+1) + "/" + String.valueOf(nam));
                    }
                });
            }
        });
        builder.setView(view)
                .setTitle("Chon Ngay")
                .setNeutralButton("Nhap", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(BEFORE_DAY_RESULT_KEY, tv_date.getText().toString());
                        setResult(BEFORE_DAY_RESULT_CODE, returnIntent);
                        finish();
                    }

                })
                .setNegativeButton("Huy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }

    private Dialog AfterDateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.datefilterbyday, null);
        TextView tv_title = (TextView) view.findViewById(R.id.filter_date_tv_title);

        TextView tv_date = (TextView) view.findViewById(R.id.filter_date_tv_date);

        tv_title.setText("Sau");
        tv_date.setText("Nhan de chon");
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDatePickerDialog(FilterbydayActivity.this, tv_add_after_day_listener = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int nam, int thang, int ngay) {

                        //+1 tháng do tháng tính từ 0->11
                        tv_date.setText(String.valueOf(ngay)+ "/" + String.valueOf(thang+1) + "/" + String.valueOf(nam));
                    }
                });
            }
        });
        builder.setView(view)
                .setTitle("Chọn ngay")
                .setNeutralButton("Nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent returntIntent = new Intent();
                        returntIntent.putExtra( AFTER_DAY_RESULT_KEY,tv_date.getText().toString());
                        setResult(AFTER_DAY_RESULT_CODE, returntIntent);
                        finish();
                    }
                })
                .setNegativeButton("Huy", new DialogInterface.OnClickListener(){
                    @Override
                    public  void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id){
            case BEFORE_DAY_Calendar_id:
                dialog = BeforeDateDialog();
                break;
            case AFTER_DAY_Calendar_id:
                dialog = AfterDateDialog();
                break;

        }
        //return super.onCreateDialog(id);
        return dialog;
    }
    private void  eventListener(){
        before_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(BEFORE_DAY_Calendar_id); }
        });
        after_day.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("click", "true");
                showDialog(AFTER_DAY_Calendar_id);
            }
        });
        inRangeDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog(IN_DAY_Calendar_id);
                DialogFilterBydayRangeActivity DialogFilterByDayRange = new DialogFilterBydayRangeActivity();
                DialogFilterByDayRange.show(getSupportFragmentManager(),null);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterbyday);
        getViews();
        eventListener();
    }
}
