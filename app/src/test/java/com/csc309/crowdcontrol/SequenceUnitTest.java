package com.csc309.crowdcontrol;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SequenceUnitTest{
    public SequenceUnitTest() {
    }


    @Test
    public void Sequence_Length() throws Exception {
        BeatMap beatMap = new BeatMap();
        Sequencer MyTest = new Sequencer(beatMap);
        float result = MyTest.lengthToFloat(BeatMap.NOTE_LENGTH.WHOLE);
        assertEquals(result,  result, 0.1);
    }

    @Test
    public void Sequence_dirToString() throws Exception {
        BeatMap beatMap = new BeatMap();
        Sequencer MyTest = new Sequencer(beatMap);
        String result = MyTest.dirToString(Arrow.DIRECTION.DOWN);
        assertEquals("DOWN",  result);
    }


    @Test
    public void ArrowTest() throws Exception {
        Arrow arrow = new Arrow(Arrow.DIRECTION.DOWN, 5, 200, 200, 200);
        Boolean result = arrow.shouldDelete();
        assertFalse(result);
    }
    //want to test score counter: if adding properly


}