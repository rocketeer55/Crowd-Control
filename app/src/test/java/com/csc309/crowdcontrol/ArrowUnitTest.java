package com.csc309.crowdcontrol;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ArrowUnitTest{
    public ArrowUnitTest() {
    }


    @Test
    public void ArrowDeleteTest() throws Exception {
        Arrow arrow = new Arrow(Arrow.DIRECTION.DOWN, 5, 200, 200, 200);
        Boolean result = arrow.shouldDelete();
        assertFalse(result);
    }

    @Test
    public void ArrowDequeueTest() throws Exception {
        Arrow arrow = new Arrow(Arrow.DIRECTION.DOWN, 5, 200, 200, 200);
        Boolean result = arrow.shouldDequeue();
        assertFalse(result);
    }
    //want to test score counter: if adding properly


}
