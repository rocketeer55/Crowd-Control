package com.csc309.crowdcontrol;

public class Note
{
    public int measure;
    public BeatMap.NOTE_LENGTH noteLength;
    public Arrow.DIRECTION arrowDirection;

    public Note(int measure, Arrow.DIRECTION dir) {
        this.measure = measure;
        arrowDirection = dir;
    }

    public Note(int measure, BeatMap.NOTE_LENGTH length, Arrow.DIRECTION dir)
    {
        this.measure = measure;
        noteLength = length;
        arrowDirection = dir;
    }

    public int getMeasure() {
        return measure;
    }
}