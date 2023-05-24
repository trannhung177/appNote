package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FilterActivity extends AppCompatActivity {
    static final int BY_TIME = 0;

    static final int BY_DATE = 1;

    TextView filterByTime, filterByDate;
    Button filter, cancel;

    String afterTime = "", beforeTime = "", afterDate = "", beforeDate = "";

    static final String BEFORE_TIME_EXTRA_KEY = "beforeTime";
    static final String AFTER_TIME_EXTRA_KEY = "afterTime";
    static final String AFTER_DATE_EXTRA_KEY = "afterDate";
    static final String BEFORE_DATE_EXTRA_KEY = "beforeDate";

    private void getViews() {
        this.filterByTime = findViewById(R.id.tv_filterByTime);
        this.filterByDate = findViewById(R.id.tv_filterByDate);
        this.filter = findViewById(R.id.btn_filter);
        this.cancel = findViewById(R.id.btn_cancel);
    }

    private void eventListener() {
        filterByTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, FilterByTimeActivity.class);
                startActivityForResult(intent, BY_TIME);
            }
        });

        filterByDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, FilterbydayActivity.class);
                startActivityForResult(intent, BY_DATE);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterActivity.this, LichHenActivity.class);

                intent.putExtra(BEFORE_TIME_EXTRA_KEY, beforeTime);
                intent.putExtra(AFTER_TIME_EXTRA_KEY, afterTime);
                intent.putExtra(BEFORE_DATE_EXTRA_KEY, beforeDate);
                intent.putExtra(AFTER_DATE_EXTRA_KEY, afterDate);

                setResult(RESULT_OK, intent);
                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Nhận kết quả trả về từ FilterByTimeActivity
    public void getActivityResultTime(int resultCode, Intent data) {
        switch (resultCode) {
            case FilterByTimeActivity.AFTER_TIME_RESULT_CODE:
                afterTime= data.getStringExtra(FilterByTimeActivity.AFTER_TIME_RESULT_KEY);
                beforeTime="";
                this.filterByTime.setText("Sau " + afterTime);
                break;
            case FilterByTimeActivity.BEFORE_TIME_RESULT_CODE:
                beforeTime= data.getStringExtra(FilterByTimeActivity.BEFORE_TIME_RESULT_KEY);
                afterTime="";
                this.filterByTime.setText("Trước " + beforeTime);
                break;
            case FilterByTimeActivity.IN_RANGE_TIME_RESULT_CODE:
                beforeTime= data.getStringExtra(FilterByTimeActivity.BEFORE_TIME_RESULT_KEY);
                afterTime= data.getStringExtra(FilterByTimeActivity.AFTER_TIME_RESULT_KEY);
                this.filterByTime.setText("Từ " + afterTime + " trước " + beforeTime);
                break;
        }
    }

    public void getActivityResultDate(int resultCode, Intent data) {
        switch (resultCode) {
            case FilterbydayActivity.AFTER_DAY_RESULT_CODE:
                afterDate= data.getStringExtra(FilterbydayActivity.AFTER_DAY_RESULT_KEY);
                beforeDate="";
                this.filterByDate.setText("Sau " + afterDate);
                break;
            case FilterbydayActivity.BEFORE_DAY_RESULT_CODE:
                beforeDate= data.getStringExtra(FilterbydayActivity.BEFORE_DAY_RESULT_KEY);
                afterDate="";
                this.filterByDate.setText("Trước " + beforeDate);
                break;
            case  FilterbydayActivity.IN_DAY_RESULT_CODE:
                beforeDate= data.getStringExtra(FilterbydayActivity.BEFORE_DAY_RESULT_KEY);
                afterDate= data.getStringExtra(FilterbydayActivity.AFTER_DAY_RESULT_KEY);
                this.filterByDate.setText("Từ " + afterDate + " trước " + beforeDate);
                break;
        }
    }

    // Nhận kết quả trả về
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == BY_DATE) {
            getActivityResultDate(resultCode,data);
        } else {
            getActivityResultTime(resultCode,data);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getViews();
        eventListener();
    }
}
