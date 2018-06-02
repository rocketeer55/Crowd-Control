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
        beatMap.qTest();
    }

    public void performFileTest(View view)
    {
        try
        {
            BeatMap beatMap = new BeatMap(R.raw.thethrillbeatmap, getApplicationContext());
        }

        catch(FileNotFoundException e)
        {
            System.out.println("FILE NOT FOUND");
        }

    }

    public void performMIDIReadTest(View view)
    {
        MIDIReader.readMidi(getApplicationContext(), R.raw.thethrillmidi);
    }
}
