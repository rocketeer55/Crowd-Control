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
        score = new UpdateScore(screenWidth);
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

    @Test
    public void TestArrowLoopEnteredOnce() throws Exception
    {
        LinkedList<Arrow> arrowList = new LinkedList<>();
        float songPos = 0;
        int screenWidth = 10;
        UpdateScore score;
        score = new UpdateScore(screenWidth);
        Arrow arrow1 = new Arrow(Arrow.DIRECTION.DOWN, 5, 200, 200, 200);
        arrowList.add(arrow1);
        int LoopEnterCount = 0;
        for (Arrow arr : arrowList)
        {
            arr.update(songPos);
            if(arr.wasMissed == true) {
                score.incrementMissedCount();
            }
            LoopEnterCount++;
        }
        assertEquals(LoopEnterCount, 1);
    }

    @Test
    public void TestArrowLoopEnteredTwice() throws Exception
    {
        LinkedList<Arrow> arrowList = new LinkedList<>();
        float songPos = 0;
        int screenWidth = 10;
        UpdateScore score;
        score = new UpdateScore(screenWidth);
        Arrow arrow1 = new Arrow(Arrow.DIRECTION.DOWN, 5, 200, 200, 200);
        arrowList.add(arrow1);
        Arrow arrow2 = new Arrow(Arrow.DIRECTION.UP, 5, 200, 200, 200);
        arrowList.add(arrow2);
        int LoopEnterCount = 0;
        for (Arrow arr : arrowList)
        {
            arr.update(songPos);
            if(arr.wasMissed == true) {
                score.incrementMissedCount();
            }
            LoopEnterCount++;
        }
        assertEquals(LoopEnterCount, 2);
    }

    @Test
    public void TestArrowLoopEnteredAvg() throws Exception
    {
        LinkedList<Arrow> arrowList = new LinkedList<>();
        float songPos = 0;
        int screenWidth = 10;
        UpdateScore score;
        score = new UpdateScore(screenWidth);
        Arrow arrow1 = new Arrow(Arrow.DIRECTION.DOWN, 5, 200, 200, 200);
        arrowList.add(arrow1);
        Arrow arrow2 = new Arrow(Arrow.DIRECTION.UP, 5, 200, 200, 200);
        arrowList.add(arrow2);
        Arrow arrow3 = new Arrow(Arrow.DIRECTION.LEFT, 5, 200, 200, 200);
        arrowList.add(arrow3);
        Arrow arrow4 = new Arrow(Arrow.DIRECTION.RIGHT, 5, 200, 200, 200);
        arrowList.add(arrow4);
        Arrow arrow5 = new Arrow(Arrow.DIRECTION.UP, 5, 200, 200, 200);
        arrowList.add(arrow5);
        Arrow arrow6 = new Arrow(Arrow.DIRECTION.UP, 5, 200, 200, 200);
        arrowList.add(arrow6);
        Arrow arrow7 = new Arrow(Arrow.DIRECTION.LEFT, 5, 200, 200, 200);
        arrowList.add(arrow7);
        Arrow arrow8 = new Arrow(Arrow.DIRECTION.RIGHT, 5, 200, 200, 200);
        arrowList.add(arrow8);
        Arrow arrow9 = new Arrow(Arrow.DIRECTION.DOWN, 5, 200, 200, 200);
        arrowList.add(arrow9);
        Arrow arrow10 = new Arrow(Arrow.DIRECTION.UP, 5, 200, 200, 200);
        arrowList.add(arrow10);
        int LoopEnterCount = 0;
        for (Arrow arr : arrowList)
        {
            arr.update(songPos);
            if(arr.wasMissed == true) {
                score.incrementMissedCount();
            }
            LoopEnterCount++;
        }
        assertEquals(LoopEnterCount, 10);
    }
}
