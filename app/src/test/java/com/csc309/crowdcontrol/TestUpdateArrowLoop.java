package com.csc309.crowdcontrol;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class TestUpdateArrowLoop {
    @Test
    public void TestArrowLoopNotEntered() throws Exception
    {
        LinkedList<Arrow> arrowList = new LinkedList<>();
        float songPos = 0;
        int screenWidth = 10;
        boolean enteredLoop = false;
        UpdateScore score;
        score = new UpdateScore(screenWidth, 1);
        for (Arrow arr : arrowList)
        {
            enteredLoop = true;
            arr.update(songPos);
            if(arr.wasMissed == true) {
                score.incrementMissedCount();
            }
        }
        assertFalse(enteredLoop);
    }
}
