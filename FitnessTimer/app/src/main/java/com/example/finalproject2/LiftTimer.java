package com.example.finalproject2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LiftTimer extends WearableActivity {

    TextView timerView;
    TextView liftSetCounter;
    Button start;
    CountDownTimer timer;
    long countdownTime = 0;
    boolean timerRunning;
    int currSetNum = 0;
    long pauseMillSecsUntilFinish;
    boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lift_timer);

        timerView = findViewById(R.id.timerView);
        timerView.setText(Long.toString(countdownTime / 1000));

        liftSetCounter = findViewById(R.id.setCounter);
        liftSetCounter.setText("Current Set #: " + currSetNum);

        start = findViewById(R.id.startButton);
        // Enables Always-on
        setAmbientEnabled();
    }


    public void onClickStart(View view) {
        if(timerRunning){
            //pauses timer
            start.setText("Resume");
            paused = true;
            stopTimer();
        }
        //if paused
        else if(paused == true){
            start.setText("Stop");
            resumeTimer(pauseMillSecsUntilFinish);
        }
        //if at reset
        else{
            start.setText("Stop");
            startTimer();
        }
    }

    private void startTimer() {
        timer = new CountDownTimer(countdownTime, 1000) {

            public void onTick(long millisUntilFinished) {
                timerView.setText("seconds remaining: " + millisUntilFinished / 1000);
                pauseMillSecsUntilFinish = millisUntilFinished;
            }

            public void onFinish() {
                timerView.setText("done!");
            }
        }.start();
        timerRunning = true;
    }

    private void resumeTimer(long timeLeftAtPause) {
        timer = new CountDownTimer(timeLeftAtPause, 1000) {

            public void onTick(long millisUntilFinished) {
                timerView.setText("seconds remaining: " + millisUntilFinished / 1000);
                pauseMillSecsUntilFinish = millisUntilFinished;
            }

            public void onFinish() {
                timerView.setText("done!");
            }
        }.start();
        timerRunning = true;
    }

    private void stopTimer() {
        //currently cancels countdown timer;
        //Want to stop and save current time;
        timer.cancel();
        timerRunning = false;
    }

    public void onClickAddTime(View view) {
        countdownTime += 30000;
        if(timerRunning == false) timerView.setText(Long.toString(countdownTime / 1000));
    }

    public void onClickSubtractTime(View view) {
        if(countdownTime >= 30000) countdownTime -= 30000;
        if(timerRunning == false) timerView.setText(Long.toString(countdownTime / 1000));
    }

    public void onClickReset(View view) {
        if(timerRunning){
            stopTimer();
        }
        start.setText("Start");
        paused = false;
        timerView.setText(Long.toString(countdownTime / 1000));
    }

    public void onClickAddSet(View view) {
        currSetNum++;
        liftSetCounter.setText("Current Set #: " + currSetNum);
    }

    public void onClickResetSetCount(View view) {
        currSetNum = 0;
        liftSetCounter.setText("Current Set #: " + currSetNum);
    }
}