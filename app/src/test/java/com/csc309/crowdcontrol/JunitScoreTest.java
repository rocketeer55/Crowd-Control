package com.csc309.crowdcontrol;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

public class JunitScoreTest {
    public JunitScoreTest() {

    }
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
        tester.updateScore(10);
        score_tester = tester.score;
        assertEquals(score_tester, 500);
    }

    @Test
    public void ScoreUpdater_Good() throws Exception {
        UpdateScore tester = new UpdateScore(200, 200 );
        int score_tester = tester.score;
        tester.updateScore(50);
        score_tester = tester.score;
        assertEquals(score_tester, 250);
    }
    @Test
    public void ScoreUpdater_Okay() throws Exception {
        UpdateScore tester = new UpdateScore(200, 200 );
        int score_tester = tester.score;
        tester.updateScore(70);
        score_tester = tester.score;
        assertEquals(score_tester, 100);
    }
}
