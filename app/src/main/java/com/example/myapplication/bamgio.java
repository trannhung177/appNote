package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class bamgio extends AppCompatActivity {
    TextView timerText;
    Button stopStartButton;
    Timer timer;
    TimerTask timerTask;
    int time = 0; //Lưu giá trị theo số giây
    boolean timerStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bamgio);
        timerText= (TextView) findViewById(R.id.timerText);
        stopStartButton=(Button) findViewById(R.id.startStopButton);

        timer = new Timer();
    }

    public void resetTapped(View view)
    {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("reset Timer");
        resetAlert.setMessage("are you sure you want to reset the timer?");
        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(timerTask !=null){
                    timerTask.cancel();
                    setButtonUI("START",R.color.green);
                    time=0;
                    timerStarted = false;
                    timerText.setText(formatTime(0,0,0));
                }
            }
        });
        resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        resetAlert.show();
    }

    public void startStopTapped(View view){
        if(timerStarted == false){
            timerStarted = true;
            setButtonUI("STOP",R.color.red);
            startTimer();
        }
        else {
            timerStarted = false;
            setButtonUI("START", R.color.green);
            timerTask.cancel();

            //tb gio
            AlertDialog.Builder builder = new AlertDialog.Builder(bamgio.this);

            builder.setMessage("Dồng hồ đã chạy được: " + getTimerText())
                    .setNegativeButton("Quay lại bấm giờ", null);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void setButtonUI(String start , int color) {
        stopStartButton.setText(start);
        //Lỗi: Ở trên truyền r.color rồi thì ở dưới này chỉ cần truyền thẳng color vào setTextColor
//        stopStartButton.setTextColor(ContextCompat.getColor(this, color));
        stopStartButton.setTextColor(color);
    }
    //
    private void startTimer() {
        timerTask = new TimerTask(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Tự động dừng khi đến 10 giây
                        if(time == 10){
                            timerStarted = false;
                            setButtonUI("START", R.color.green);
                            timerTask.cancel();
                        }
                        else{
                            time++;
                            timerText.setText(getTimerText());
                        }
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);
        int seconds =((rounded % 86400)%3600)%60;
        int minutes = ((rounded % 86400)%3600)/60;
        int hours =((rounded % 86400)/3600);
        return formatTime(seconds, minutes, hours);
    }
    //
    private String formatTime(int seconds, int minutes, int hours) {
        //Lỗi lồng string format trong nhau
//      return String.format("%02d",hours+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds));
        return String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }
}
