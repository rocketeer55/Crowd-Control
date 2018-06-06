package com.csc309.crowdcontrol;
import org.junit.Test;
import static org.junit.Assert.*;


public class BeatMapHorzUnit {
    public BeatMapHorzUnit() {

    }
    @Test
    public void BeatArrowLEFTTest() throws Exception{
        BeatMap beat = new BeatMap();
        assertTrue(Arrow.DIRECTION.LEFT == beat.stringToArrowDir("LEFT"));
    }

    @Test
    public void BeatArrowRIGHTTest() throws Exception{
        BeatMap beat = new BeatMap();
        assertTrue(Arrow.DIRECTION.RIGHT == beat.stringToArrowDir("RIGHT"));
    }
}
