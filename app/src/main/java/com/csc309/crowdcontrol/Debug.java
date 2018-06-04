package com.csc309.crowdcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Debug extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
    }

    public void performQTest()
    {
        // Something would be done here, but it smells.
    }

    public void performFileTest()
    {
        // Something would happen here too, but it also smells.

    }

    public void performMIDIReadTest()
    {
        MIDIReader.readMidi(getApplicationContext(), R.raw.thethrillmidi);
    }
}
