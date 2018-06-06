package com.csc309.crowdcontrol;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ArrowBarUnitTest{
    public ArrowBarUnitTest() {
    }


    @Test
    public void ArrowDeleteTest() throws Exception {
        ArrowColliderBar arrow = new ArrowColliderBar();
        Boolean result = arrow.shouldDelete();
        assertFalse(result);
    }

    @Test
    public void ArrowDequeueTest() throws Exception {
        ArrowColliderBar arrow = new ArrowColliderBar();
        Boolean result = arrow.shouldDequeue();
        assertFalse(result);
    }
    //want to test score counter: if adding properly


}
