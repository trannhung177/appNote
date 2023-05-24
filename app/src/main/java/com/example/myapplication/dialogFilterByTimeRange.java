package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dialogFilterByTimeRange extends AppCompatDialogFragment {
    TextView tv_after, tv_before;
    //tạo 2 listener sẽ lắng nghe 2 sự kiện
    // tuwff 2 timepicker khác nhau
    TimePickerDialog.OnTimeSetListener tv_add_before_time_listener, tv_add_after_time_listener;
    // Tạo time picker
    private void createTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, listener, currentHour, currentMinute, true);
        timePickerDialog.show();
    }

    // Hàm tạo dialog
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //tạo builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //tạo layout
        LayoutInflater inflater = getLayoutInflater();
        //tạo view
        View view = inflater.inflate(R.layout.dialog_filterbytimerange,null);
        // lấy textview từ view
        tv_after = view.findViewById(R.id.wallet_filter_time_range_after);
        tv_before = view.findViewById(R.id.wallet_filter_time_range_before);
        //tạo event cho textview
        tv_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //tạo time picker, truyền vào context, listener đã tạo từ trước
                createTimePickerDialog(getActivity(),tv_add_after_time_listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //lấy giờ và phút
                        tv_after.setText(String.valueOf(i) + ":" + String.valueOf(i1));
                    }
                });
            }
        });
        tv_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                createTimePickerDialog(getActivity(),tv_add_before_time_listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tv_before.setText(String.valueOf(i) + ":" + String.valueOf(i1));
                    }
                });
            }
        });
        // lưu view vào builder
        builder.setView(view)
                .setTitle("Chọn thời gian")
                .setNeutralButton("Nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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

    // Hàm này sẽ lắng nghe sự kiện khi nhấn nút nhập
    @Override
    public void onResume() {
        super.onResume();
        // lấy du lieu tu dialog
        final AlertDialog d = (AlertDialog) getDialog();
        if(d!=null) {
            // lấy nút nhập
            Button neutralButton = (Button) d.getButton(Dialog.BUTTON_NEUTRAL);
            // thêm sự kiện cho nút nhập
            neutralButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // tạo boolen check có đóng hay không
                    Boolean isClose = false;
                    //lây giá trị từ textview
                    String after = tv_after.getText().toString();
                    String before = tv_before.getText().toString();
                    //convert sang Time
                    Time tAfter = null, tBefore = null;
                    DateFormat formatter = new SimpleDateFormat("HH:mm");
                    try {
                        tAfter =  new Time(formatter.parse(after).getTime());
                        tBefore =  new Time(formatter.parse(before).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // so sánh giờ after phải trươc giờ before
                    if (!tAfter.after(tBefore)) {
                        //có thì đổi đk đóng sang true
                        isClose = true;
                        //tạo intent và lưu dữ liệu vào intent
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(FilterByTimeActivity.BEFORE_TIME_RESULT_KEY,before );
                        returnIntent.putExtra(FilterByTimeActivity.AFTER_TIME_RESULT_KEY,after );
                        getActivity().setResult(FilterByTimeActivity.IN_RANGE_TIME_RESULT_CODE, returnIntent);
                        // đóng activity
                        getActivity().finish();
                    } else {
                        // nếu sai thì hiện thông báo
                        Toast.makeText(getActivity(), "Thời gian bắt đầu phải trước thời gian kết thúc!", Toast.LENGTH_LONG).show();
                    }
                    if (isClose) {
                        // đóng dialog
                        d.dismiss();
                    }
                }
            });
        }
    }
}
