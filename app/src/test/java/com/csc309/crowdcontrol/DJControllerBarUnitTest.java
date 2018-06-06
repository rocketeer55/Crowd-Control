package com.csc309.crowdcontrol;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DJControllerBarUnitTest{
    public DJControllerBarUnitTest() {
    }


    @Test
    public void DJDeleteTest() throws Exception {
        DJControllerBar dj = new DJControllerBar();
        Boolean result = dj.shouldDelete();
        assertFalse(result);
    }

    @Test
    public void DJDequeueTest() throws Exception {
        DJControllerBar dj = new DJControllerBar();
        Boolean result = dj.shouldDequeue();
        assertFalse(result);
    }
    //want to test score counter: if adding properly


}
