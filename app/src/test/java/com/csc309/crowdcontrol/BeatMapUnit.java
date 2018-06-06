package com.csc309.crowdcontrol;
import org.junit.Test;
import static org.junit.Assert.*;


public class BeatMapUnit {
    public BeatMapUnit() {

    }
    @Test
    public void BeatArrowUPTest() throws Exception{
        BeatMap beat = new BeatMap();
        assertTrue(Arrow.DIRECTION.UP == beat.stringToArrowDir("UP"));
    }

    @Test
    public void BeatArrowDOWNTest() throws Exception{
        BeatMap beat = new BeatMap();
        assertTrue(Arrow.DIRECTION.DOWN == beat.stringToArrowDir("DOWN"));
    }
}
