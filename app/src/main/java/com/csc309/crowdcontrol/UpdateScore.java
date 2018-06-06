package com.csc309.crowdcontrol;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class UpdateScore extends GameObject {
    private int screenWidth;
    private Paint paint;

    public int score = 0;

    // multipliers
    private int multiplier = 1;
    private int noteStreak = 0;
    private int missedCount = 0;

    private float songPos;


    // score hits and points
    private static final int OKAY = 100;
    private static final int GOOD = 250;
    private static final int EXCELLENT = 500;

    public UpdateScore(int screenWidth){

        this.screenWidth = screenWidth;

        paint = new Paint();
    }

    public UpdateScore(int screenWidth, int test)
    {
        this.screenWidth = screenWidth;
    }

    public void draw(Canvas canvas){
        if (canvas == null) {return;}

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, screenWidth - 1, 100, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(48f);
        canvas.drawText("Score: " + score, 20,80,paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(48f);
        canvas.drawText("Multipler: " + multiplier + "x", screenWidth/2,80,paint);


        }

    public void update(float songPos){
        this.songPos = songPos;

        if(noteStreak >= 10) {
            multiplier = 8;
        }
        else if(noteStreak >= 7) {
            multiplier = 4;
        }
        else if(noteStreak >= 4) {
            multiplier = 2;
        }
        else {
            multiplier = 1;
        }
    }

    public void updateScore(float arrowPos) {
        // calculate delta
        float delta = Math.abs(songPos - arrowPos);
        if (33 >= delta && delta >= 0) {
            score += EXCELLENT * multiplier;
        }
        else if (66 >= delta && delta > 33) {
            score += GOOD * multiplier;
        }
        else {
            score += OKAY * multiplier;
        }

        incrementNoteStreak();
    }

    public void incrementNoteStreak() {
        noteStreak++;
    }

    public void resetNoteStreak() {
        noteStreak = 0;
    }

    public void incrementMissedCount() {
        missedCount++;
    }

    public void resetMissedCount() {
        missedCount = 0;
    }

    public int getMissedCount() {
        return missedCount;
    }

    public int getScore() {
        return score;
    }

    public boolean shouldDelete(){
        return false;

    }

    public boolean shouldDequeue(){
        return false;
    }
}
