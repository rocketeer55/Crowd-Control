package com.csc309.crowdcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.FileNotFoundException;

public class Debug extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
    }

    public void performQTest(View view)
    {
        BeatMap beatMap = new BeatMap();
    }

    public void performFileTest(View view)
    {
        BeatMap beatMap = new BeatMap(R.raw.thethrillbeatmap, getApplicationContext());

    }

    public void performMIDIReadTest(View view)
    {
        MIDIReader.readMidi(getApplicationContext(), R.raw.thethrillmidi);
    }
}
