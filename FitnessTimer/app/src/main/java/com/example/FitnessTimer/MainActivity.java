package com.example.FitnessTimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private Button lapTimerButton;
    private Button liftTimerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        lapTimerButton = findViewById(R.id.laptimerbutton);
        lapTimerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openLapTimer();
            }
        });
        liftTimerButton = findViewById(R.id.lifttimerbutton);
        liftTimerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openLiftTimer();
            }
        });
        // Enables Always-on
        setAmbientEnabled();
    }
    public void openLapTimer(){
        Intent intent = new Intent(this, LapTimer.class);
        startActivity(intent);
    }
    public void openLiftTimer(){
        Intent intent = new Intent(this, LiftTimer.class);
        startActivity(intent);
    }
}