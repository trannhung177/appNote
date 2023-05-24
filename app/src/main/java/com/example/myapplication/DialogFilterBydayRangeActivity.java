package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogFilterBydayRangeActivity extends AppCompatDialogFragment {
    TextView tv_after, tv_before;
    DatePickerDialog.OnDateSetListener tv_add_after_date_listener, tv_add_before_date_listener;

    private void createDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, year, month, day);
        datePickerDialog.show();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //tạo layout
        LayoutInflater inflater = getLayoutInflater();
        //tạo view
        View view = inflater.inflate(R.layout.datefilterbydayrange, null);
        // lấy textview từ view
        tv_after = view.findViewById(R.id.wallet_filter_day_range_after);
        tv_before = view.findViewById(R.id.wallet_filter_day_range_before);
        //tạo event cho textview
        tv_after.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tạo time picker, truyền vào context, listener đã tạo từ trước
                createDatePickerDialog(getActivity(), tv_add_after_date_listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int nam, int thang, int ngay) {
                        //Tháng +1 do tháng trả từ 0-11
                        tv_after.setText(String.valueOf(ngay) + "/" + String.valueOf(thang+1) +  "/" + String.valueOf(nam));
                    }
                });
            }
        });
        tv_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDatePickerDialog(getActivity(), tv_add_before_date_listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int nam, int thang, int ngay) {
                        //Tháng +1 do tháng trả từ 0-11
                        tv_before.setText(String.valueOf(ngay) + "/" + String.valueOf(thang+1) +  "/" + String.valueOf(nam));
                    }
                });
            }
        });
        builder.setView(view)
                .setTitle("Chọn ngay")
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

    @Override
    public void onResume() {
        super.onResume();
        // lấy du lieu tu dialog
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
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
                    //convert sang Date
                    Date tAfter = null, tBefore = null;
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
                    try {
                        tAfter = new SimpleDateFormat("dd/MM/yyyy").parse(after);
                        tBefore = new SimpleDateFormat("dd/MM/yyyy").parse(before);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    // so sánh giờ after phải trươc giờ before
                    if (!tAfter.after(tBefore)) {
                        //có thì đổi đk đóng sang true
                        isClose = true;
                        //tạo intent và lưu dữ liệu vào intent
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra(FilterbydayActivity.BEFORE_DAY_RESULT_KEY, before);
                        returnIntent.putExtra(FilterbydayActivity.AFTER_DAY_RESULT_KEY, after);
                        getActivity().setResult(FilterbydayActivity.IN_DAY_RESULT_CODE, returnIntent);
                        // đóng activity
                        getActivity().finish();
                    } else {
                        // nếu sai thì hiện thông báo
                        Toast.makeText(getActivity(), "Ngay bắt đầu phải trước ngay kết thúc!", Toast.LENGTH_LONG).show();
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
