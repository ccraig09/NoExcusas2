package com.fchw.noexcusas;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class Countdown extends AppCompatActivity {
    TextView tv,mTextField;
    //textview to display the countdown
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = new TextView(this);
        mTextField = findViewById(R.id.tv2);

        this.setContentView(tv);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTextField.setText("done!");
            }
        }.start();
//5000 is the starting number (in milliseconds)
//1000 is the number to count down each time (in milliseconds)
        MyCount counter = new MyCount(2592000000L,86400000L);
        counter.start();
    }
    //countdowntimer is an abstract class, so extend it and fill in methods
    public class MyCount extends CountDownTimer{
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            tv.setText("Done");
        }
        @Override
        public void onTick(long millisUntilFinished) {
            tv.setText("Left: " + millisUntilFinished/86400000);
        }
    }
}