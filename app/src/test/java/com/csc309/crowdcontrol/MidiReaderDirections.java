package com.csc309.crowdcontrol;
import org.junit.Test;
import static org.junit.Assert.*;

public class MidiReaderDirections {
    public MidiReaderDirections() {

    }
    @Test
    public void MIDIhexToDirUP() {
        MIDIReader md = new MIDIReader();
        assertEquals(Arrow.DIRECTION.UP, md.hexToDir("4c"));
    }

    @Test
    public void MIDIhexToDirLEFT() {
        MIDIReader md = new MIDIReader();
        assertEquals(Arrow.DIRECTION.LEFT, md.hexToDir("53"));
    }
}