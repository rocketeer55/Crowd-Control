package com.csc309.crowdcontrol;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class TestUpdateArrowLoop {
    @Test
    public void TestArrowLoopNotEntered throws Exception
    {
        LinkedList<Arrow> arrowList = new LinkedList<>();
        float songPos = 0;
        UpdateScore score;
        score = new UpdateScore(getContext(), screenWidth, screenHeight);
        for (Arrow arr : arrowList)
        {
            arr.update(songPos);
            if(arr.wasMissed == true) {
                score.incrementMissedCount();
            }
        }
    }


}
