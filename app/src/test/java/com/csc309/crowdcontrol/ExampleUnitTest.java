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
public class ExampleUnitTest{
    public ExampleUnitTest() {
    }


    @Test
    public void Sequence_Length() throws Exception {
        BeatMap beatMap = new BeatMap();
        Sequencer MyTest = new Sequencer(beatMap);
        float result = MyTest.lengthToFloat(BeatMap.NOTE_LENGTH.WHOLE);
        assertEquals(result,  result, 0.1);

    }

    @Test
    public void ArrowTest() throws Exception {
        Arrow arrow = new Arrow(Arrow.DIRECTION.DOWN, 5, 200, 200, 200);
        Boolean result = arrow.shouldDelete();
        assertFalse(result);
    }
    //want to test score counter: if adding properly

    @Test
    public void ScoreUpdater_initial() throws Exception {
        UpdateScore tester = new UpdateScore(200, 200 );
        int score_tester = tester.score;
        assertEquals(score_tester, 0);

    }
    @Test
    public void ScoreUpdater_Excellent() throws Exception {
        UpdateScore tester = new UpdateScore(200, 200 );
        int score_tester = tester.score;
        tester.update(0.1f);
        score_tester = tester.score;
        assertEquals(score_tester, 500);
    }

    @Test
    public void ScoreUpdater_Good() throws Exception {
        UpdateScore tester = new UpdateScore(200, 200 );
        int score_tester = tester.score;
        tester.update(0.5f);
        score_tester = tester.score;
        assertEquals(score_tester, 250);
    }
    @Test
    public void ScoreUpdater_Okay() throws Exception {
        UpdateScore tester = new UpdateScore(200, 200 );
        int score_tester = tester.score;
        tester.update(0.7f);
        score_tester = tester.score;
        assertEquals(score_tester, 100);
    }
}