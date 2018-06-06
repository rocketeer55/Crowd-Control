package com.csc309.crowdcontrol;
import org.junit.Test;
import static org.junit.Assert.*;


public class MusicThreadTest {
    public MusicThreadTest() {

    }
    @Test
    public void MusicRunning() {
        MusicThread mt = new MusicThread();
        mt.setRunning(true);
        assertTrue(mt.checkRunning());
    }
}

