package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class FilterByTimeActivity extends AppCompatActivity {

    // id của dialog
    private static final int BEFORE_TIME_DIALOG_ID = 1;
    private static final int AFTER_TIME_DIALOG_ID = 2;
    private static final int IN_RANGE_TIME_DIALOG_ID = 3;

    // code để check là từ filter nào
    public static final int BEFORE_TIME_RESULT_CODE = 4;
    public static final int AFTER_TIME_RESULT_CODE = 5;
    public static final int IN_RANGE_TIME_RESULT_CODE = 6;

    // key để trả ve giá trị cho activity cha
    public static final String BEFORE_TIME_RESULT_KEY = "beforeTime";
    public static final String AFTER_TIME_RESULT_KEY = "afterTime";
    public static final String IN_RANGE_TIME_RESULT_KEY = "inRange";

    private TextView beforeTime, afterTime, inRangeTime;

    TimePickerDialog.OnTimeSetListener tv_add_before_time_listener, tv_add_after_time_listener;

    private void getViews(){
        this.beforeTime = findViewById(R.id.tv_beforeTime);
        this.afterTime = findViewById(R.id.tv_afterTime);
        this.inRangeTime = findViewById(R.id.tv_inRangeTime);
    }

    private void eventListener() {
        beforeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(BEFORE_TIME_DIALOG_ID);
            }
        });

        afterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(AFTER_TIME_DIALOG_ID);
            }
        });

        inRangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFilterByTimeRange DialogFilterByTimeRange = new dialogFilterByTimeRange();
                DialogFilterByTimeRange.show(getSupportFragmentManager(),null);
            }
        });
    }

    //    Tạo time picker (Đồng hồ chọn giờ)
    private void createTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, listener, currentHour, currentMinute, true);
        timePickerDialog.show();
    }

    //    Tạo dialog before cho time picker
    private Dialog BeforeTimeDialog() {
        // phần cmt trong này sẽ như bên dialogfilterbytimerange
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filterbytime,null);
        TextView tv_title = view.findViewById(R.id.filter_time_tv_title);
        TextView tv_time = view.findViewById(R.id.filter_time_tv_time);

        tv_title.setText("Trước");
        tv_time.setText("Nhấn để chọn");
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTimePickerDialog(FilterByTimeActivity.this, tv_add_before_time_listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tv_time.setText(String.valueOf(i) + ":" + String.valueOf(i1));
                    }
                });
            }
        });
        builder.setView(view)
                .setTitle("Chọn thời gian")
                .setNeutralButton("Nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(BEFORE_TIME_RESULT_KEY, tv_time.getText().toString());
                        setResult(BEFORE_TIME_RESULT_CODE,returnIntent);
                        finish();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }

    private Dialog AfterTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_filterbytime,null);
        TextView tv_title = view.findViewById(R.id.filter_time_tv_title);
        TextView tv_time = view.findViewById(R.id.filter_time_tv_time);

        tv_title.setText("Sau");
        tv_time.setText("Nhấn để chọn");
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTimePickerDialog(FilterByTimeActivity.this, tv_add_after_time_listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tv_time.setText(String.valueOf(i) + ":" + String.valueOf(i1));
                    }
                });
            }
        });
        builder.setView(view)
                .setTitle("Chọn thời gian")
                .setNeutralButton("Nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent returntIntent = new Intent();
                        returntIntent.putExtra(AFTER_TIME_RESULT_KEY,tv_time.getText().toString());
                        Log.e("time", tv_time.getText().toString());
                        setResult(AFTER_TIME_RESULT_CODE, returntIntent);
                        finish();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }

    // ghi đè phương thức tạo dialog
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        //check id dialog xem là dialog nào
        // để tạo dialog theo id
        switch (id) {
            case BEFORE_TIME_DIALOG_ID:
                dialog = BeforeTimeDialog();
                break;
            case AFTER_TIME_DIALOG_ID:
                dialog = AfterTimeDialog();
                break;
        }
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterbytime);
        getViews();
        eventListener();
    }

}
