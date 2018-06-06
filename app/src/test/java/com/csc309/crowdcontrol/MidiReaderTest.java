package com.csc309.crowdcontrol;
import org.junit.Test;
import static org.junit.Assert.*;

public class MidiReaderTest {
    public MidiReaderTest() {

    }
    @Test
    public void MIDIhexToDir() {
        MIDIReader md = new MIDIReader();
        assertEquals(Arrow.DIRECTION.DOWN, md.hexToDir("48"));
    }

    @Test
    public void MIDIempty() {
        MIDIReader md = new MIDIReader();
        assertTrue(md.isEmpty());
    }
}
