package com.csc309.crowdcontrol;

import org.junit.Test;
import static org.junit.Assert.*;

public class JunitScoreTest {
    public JunitScoreTest() {

    }
    @Test
    public void ScoreUpdater_initial() throws Exception {
        UpdateScore tester = new UpdateScore(200);
        int score_tester = tester.score;
        assertEquals(0, score_tester, 0);
    }
    @Test
    public void ScoreUpdater_Excellent() throws Exception {
        UpdateScore tester = new UpdateScore(200);
        int score_tester = tester.score;
        tester.updateScore(10);
        score_tester = tester.score;
        assertEquals(500, score_tester, 10);
    }

    @Test
    public void ScoreUpdater_Good() throws Exception {
        UpdateScore tester = new UpdateScore(200);
        int score_tester = tester.score;
        tester.updateScore(50);
        score_tester = tester.score;
        assertEquals(250, score_tester, 10);
    }
    @Test
    public void ScoreUpdater_Okay() throws Exception {
        UpdateScore tester = new UpdateScore(200);
        int score_tester = tester.score;
        tester.updateScore(70);
        score_tester = tester.score;
        assertEquals(100, score_tester, 10);
    }
}
