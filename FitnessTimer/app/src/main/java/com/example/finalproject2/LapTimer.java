package com.example.finalproject2;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ListView;

import java.util.ArrayList;

public class LapTimer extends WearableActivity {

    private Chronometer timeDisplay;
    private ListView list;
    private boolean running;
    private long pauseOffset = 0;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    //TEMP
    int count = 0;
    long accumTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lap_timer);
        timeDisplay = findViewById(R.id.timeDisplay);
        list = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        list.setAdapter(adapter);

        // Enables Always-on
        setAmbientEnabled();
    }

    public void onClickStart(View view) {
        if(!running){
            timeDisplay.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            timeDisplay.start();
            running = true;
        }

    }

    public void onClickStop(View view) {
        if(running) {
            timeDisplay.stop();
            pauseOffset = SystemClock.elapsedRealtime() - timeDisplay.getBase();
            running = false;
        }
    }

    public void onClickReset(View view) {
        timeDisplay.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        onClickStop(view);
        arrayList.clear();
        adapter.notifyDataSetChanged();
        count = 0;
        accumTime = 0;
    }

    public void onClickLap(View view) {
        if(running) {
            long currTime = (SystemClock.elapsedRealtime() - timeDisplay.getBase()) - accumTime;
            accumTime += currTime;
            long hours = currTime / 3600000;
            long minutes = ((currTime / 1000) / 60) - (hours * 60);
            long seconds = (currTime / 1000) - (minutes * 60);
            String strTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            arrayList.add("Lap " + count + ":  " + strTime);
            adapter.notifyDataSetChanged();
            count++;
        }
    }
}